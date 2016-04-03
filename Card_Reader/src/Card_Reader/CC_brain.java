/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Card_Reader;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.smartcardio.*;
import static org.apache.commons.io.FilenameUtils.separatorsToSystem;

/**
 *
 * @author rofler
 */
public class CC_brain {

    static int debug = 0;

    private static CardTerminal terminal;
    private static String roomCode = "4.2.11";

    private static String serverUrl = "http://localhost:3000/report";
    //for testing
    //private static String current_card_path = separatorsToSystem("/home/rofler/current_card.json");    
    //for release
    
    private static String baseDirectory = System.getProperty("user.home");
    private static String current_card_path = separatorsToSystem(baseDirectory + "/" + "current_card.json");
    private static String current_card_photo_path = separatorsToSystem(baseDirectory + "/" + "current_card_photo.jp2");

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
            System.out.println(ex.getMessage());
            //int errorNumber = Integer.parseInt(ex.getMessage().split("Error code : -")[1]);
            System.out.println("Smartcardio Exception.");
            //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
            res = 1;
        }

        return res;
    }

    public static void main(String[] args) {
        
        

        int flag = 0;

        CC_IO ccIO = new CC_IO();

        flag = init();
        
        Database_connector db = new Database_connector("jdbc:mysql://localhost:3306/es_module", "root", "");
        

        //System.err.println(System.getProperty("os.name"));     
        //System.err.println(System.getProperty("os.name").split(" ")[0].equals("Windows"));
        while (flag == 0) {
            try {

                //wait for card to be inserted
                System.out.println("Waiting for card");
                while (terminal.isCardPresent() == false);

                //get data
                CardData card = ccIO.RunAnalisys(current_card_photo_path);
                
                //put current id in file for server
                //card.sendIDToJsonFile(current_card_path);         
                db.connect();
                db.update_curent_card(card.getNumBI());

                //send data for logging (card inserted)       
                db.dump_interaction(card, roomCode, "inserted");
                db.connection_close();
                
                //wait for card to be removed before resuming action
                System.out.println("Please remove card");
                while (terminal.isCardPresent() == true);
                
                //destroy current id file
                db.connect();
                db.update_curent_card("dummy");
                
                File f;
                //f = new File(current_card_path);
                //f.delete();
                f = new File(current_card_photo_path);
                f.delete();
                
                //send card removed info to server database
                db.dump_interaction(card, roomCode, "removed");
                db.connection_close();
                
                

            } catch (CardException ex) {
                System.out.println(ex.getMessage());
                //int errorNumber = Integer.parseInt(ex.getMessage().split("Error code : -")[1]);
                System.out.println("\n\nSmartcardio Exception.");
                //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
                //flag = 1;
            }

        }

        System.out.println("\n\nProgram is Terminating.");

    }

    private static int sendToServer(CardData card, String interaction) {

        try {
            URL url;
            url = new URL(serverUrl);
            HttpURLConnection serverConnection = (HttpURLConnection) url.openConnection();
            serverConnection.setDoOutput(true);
            serverConnection.setRequestMethod("POST");
            serverConnection.setRequestProperty("Content-Type", "application/json");

            String serviceInput =  card.getJson(roomCode, interaction);

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
            //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("\n\nI-O exception!");
            ex.printStackTrace();
            //Logger.getLogger(CC_brain.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 1;
    }

}
