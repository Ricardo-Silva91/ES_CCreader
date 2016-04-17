package Card_Reader;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.smartcardio.*;
import static org.apache.commons.io.FilenameUtils.separatorsToSystem;
import org.json.JSONObject;

/**
 * main class, controls the card reader and the messaging
 *
 * @author rofler
 */
public class CC_brain {

    static int debug = 0;

    /**
     * smart card reader terminal
     */
    private static CardTerminal terminal;

    /**
     * room code (different for every room)
     */
    private static String roomCode = "4.2.11";

    /**
     * server url (for REST broker)
     */
    private static String serverUrl = "http://localhost:3000/report";

    /**
     * server IP (for rabbitMQ broker)
     */
    private static String serverIP = "localhost";

    /**
     * queue name (for rabbitMQ broker)
     */
    private final static String QUEUE_NAME = "ES_module_rabbit";

    /**
     * base directory path (used to create database and temporary files path)
     */
    private static String baseDirectory = System.getProperty("user.home");

    /**
     * current card file (not used at the moment)
     */
    private static String current_card_path = separatorsToSystem(baseDirectory + "/" + "current_card.json");

    /**
     * path to the current card's jp2 image path (not used at the moment)
     */
    private static String current_card_photo_path = separatorsToSystem(baseDirectory + "/" + "current_card_photo.jp2");

    /**
     * path to the sqlite database file
     */
    private static String databasePath = separatorsToSystem(baseDirectory + "/" + "es_module.db");

    /**
     * this method initiates the card reader hardware interaction, must be
     * executed at the beginning of the program's life cycle.
     *
     * @return 0 if success !0 if fail
     */
    private static int init() {

        int res = 0;

        try {
            TerminalFactory tf = TerminalFactory.getDefault();
            CardTerminals ct = tf.terminals();
            List<CardTerminal> l = null;

            try {
                l = ct.list();
            } catch (Exception e) {
                System.out.println("Error listing Terminals: " + e.toString());
                throw e;
            }

            System.out.println("List of PC/SC Readers connected:");
            ListIterator it = l.listIterator();
            while (it.hasNext()) {
                System.out.println("Reader: " + ((CardTerminal) it.next()).getName());
            }

            // Pick up the first one
            String terminalName = l.get(0).getName();
            terminal = ct.getTerminal(terminalName);
            System.out.println("Terminal fetched: " + terminal.getName());

        } catch (CardException ex) {
            System.out.println("Smartcardio Exception.");
            System.out.println(ex.getMessage());
            res = 1;
        }

        return res;
    }

    /**
     * main method
     *
     * @param args may contain server IP and/or room code
     */
    public static void main(String[] args) {

        if (args.length > 0) {
            serverIP = args[0];
        }

        int flag = 0;

        CC_IO ccIO = new CC_IO();

        flag = init();

        //Database_connector_mysql db = new Database_connector_mysql("jdbc:mysql://localhost:3306/es_module", "root", "");
        Database_connector_sqlite db = new Database_connector_sqlite();

        mainCycle:
        while (flag == 0) {
            try {

                //wait for card to be inserted
                System.out.println("Waiting for card");
                while (terminal.isCardPresent() == false);

                //get data
                CardData card = ccIO.RunAnalisys(current_card_photo_path);

                if (card == null) {
                    //wait for card to be removed before resuming action
                    System.out.println("Please remove card");
                    while (terminal.isCardPresent() == true);
                    ccIO = new CC_IO();
                    flag = init();

                    continue mainCycle;
                }

                db.connect(databasePath);
                db.dump_interaction(card, roomCode, "inserted");
                db.update_curent_card(card.getNumBI());
                int id_for_json = db.getPersonId(card.getNumBI());
                db.connection_close();

                //sendToServerRabbitMQ(card, "inserted");
                sendToServerRabbitMQbrief(id_for_json, "inserted");

                //wait for card to be removed before resuming action
                System.out.println("Please remove card");
                while (terminal.isCardPresent() == true);

                //send card removed info to server & database
                //sendToServerRabbitMQ(card, "removed");
                sendToServerRabbitMQbrief(id_for_json, "removed");
                db.connect(databasePath);
                db.update_curent_card("dummy");

                File f;
                f = new File(current_card_photo_path);
                f.delete();

                //send card removed info to server database
                db.dump_interaction(card, roomCode, "removed");
                db.connection_close();

            } catch (CardException ex) {
                System.out.println(ex.getMessage());
                System.out.println("\n\nSmartcardio Exception.");
                //flag = 1;
            }

        }

        System.out.println("\n\nProgram is Terminating.");

    }

    /**
     * method used to send full card data and interaction information to broker
     * (deprecated)
     *
     * @param card current card data
     * @param interaction type of interaction
     * @return 0 if success !0 if fail
     */
    private static int sendToServerRabbitMQ(CardData card, String interaction) {

        int res = 0;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            System.out.println("server IP: " + serverIP);
            factory.setHost(serverIP);
            factory.setUsername("es");
            factory.setPassword("a");
            factory.setPort(5672);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = card.getJson(roomCode, interaction);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent interaction to broker");

            channel.close();
            connection.close();

        } catch (IOException ex) {
            //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("IO exception");
            ex.printStackTrace();
            res = -1;
        } catch (TimeoutException ex) {
            //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("time out exception");
            ex.printStackTrace();
            res = -1;
        }
        return res;
    }

    /**
     * method used to send event information to broker
     *
     * @param person_id internal id associated with card currently in reader
     * @param interaction type of interaction
     * @return 0 if success !0 if fail
     */
    private static int sendToServerRabbitMQbrief(int person_id, String interaction) {

        int res = 0;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            System.out.println("server IP: " + serverIP);
            factory.setHost(serverIP);
            factory.setUsername("es");
            factory.setPassword("a");
            factory.setPort(5672);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = makeJson(person_id, roomCode, interaction);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent interaction to broker");
            //System.out.println(" Sent: " + message);

            channel.close();
            connection.close();

        } catch (IOException ex) {
            //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("IO exception");
            ex.printStackTrace();
            res = -1;
        } catch (TimeoutException ex) {
            //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("time out exception");
            ex.printStackTrace();
            res = -1;
        }
        return res;
    }

    /**
     * method that turn event data into json string
     *
     * @param person_id internal id associated with card currently in reader
     * @param roomCode room code
     * @param interaction type of interaction
     * @return json string of event
     */
    private static String makeJson(int person_id, String roomCode, String interaction) {
        String res = null;

        JSONObject card_js = new JSONObject();
        card_js.put("id", person_id);
        card_js.put("interaction", interaction);
        card_js.put("roomCode", roomCode);
        card_js.put("time", System.currentTimeMillis());
        res = card_js.toString();

        return res;
    }

    /**
     * method used to send event to REST server (deprecated)
     *
     * @param card card information
     * @param interaction type of interaction
     * @return 0 if success !0 if fail
     */
    private static int sendToServer(CardData card, String interaction) {

        int res = 0;

        try {
            URL url;
            url = new URL(serverUrl);
            HttpURLConnection serverConnection = (HttpURLConnection) url.openConnection();
            serverConnection.setDoOutput(true);
            serverConnection.setRequestMethod("POST");
            serverConnection.setRequestProperty("Content-Type", "application/json");

            String serviceInput = card.getJson(roomCode, interaction);

            System.err.println(serviceInput);

            OutputStream outs = serverConnection.getOutputStream();
            outs.write(serviceInput.getBytes());
            outs.flush();

            if (serverConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + serverConnection.getResponseCode());
            }

            serverConnection.getResponseCode();

            BufferedReader buf = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = buf.readLine()) != null) {
                System.out.println(output);
            }

            serverConnection.disconnect();

            return 0;
        } catch (MalformedURLException ex) {
            System.err.println("\n\nMalformed URL exception!");
            ex.printStackTrace();
            res = -1;
            //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("\n\nI-O exception!");
            ex.printStackTrace();
            res = -1;
            //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

}
