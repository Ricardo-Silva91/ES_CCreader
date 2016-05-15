/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmq_clienttest;

import Util.CardData;
import Util.Card_Interaction;
import Util.Database_connector_sqlite;
import Util.Rabbit_person;
import com.rabbitmq.client.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.io.FilenameUtils.separatorsToSystem;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author rofler
 */
public class Simple_receiver {

    private static String baseDirectory = System.getProperty("user.home");
    private final static String QUEUE_NAME = "ES_module_rabbit_test";
    private static Database_connector_sqlite db;
    private static String databasePath = separatorsToSystem(baseDirectory + "/" + "es_module_rabbit_tests.db");

    public static void main(String[] argv) throws Exception {

        db = new Database_connector_sqlite();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("es");
        factory.setPassword("a");
        //factory.setVirtualHost("/");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                doWork(message);

            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

    private static int doWork(String message) {
        int res = 0;

        try {
            JSONParser parser = new JSONParser();

            JSONObject json_o;
            json_o = (JSONObject) parser.parse(message);

            Rabbit_person rabbit_person = new Rabbit_person(json_o.get("interaction").toString(), json_o.get("roomCode").toString(), json_o.get("time").toString(), json_o.get("id").toString());
            
            /*JSONObject interaction = (JSONObject) json_o.get("interaction_info");
            JSONObject person = (JSONObject) json_o.get("person_info");

            Card_Interaction card_interaction = new Card_Interaction(interaction.get("interaction").toString(), interaction.get("roomCode").toString(), interaction.get("time").toString(), person.get("numBI").toString());

            
            
            CardData card = new CardData(
                    person.get("firstname").toString(),
                    person.get("lastname").toString(),
                    person.get("birthDate").toString(),
                    person.get("height").toString(),
                    person.get("sex").toString(),
                    person.get("firstnameFather").toString(),
                    person.get("lastnameFather").toString(),
                    person.get("firstnameMother").toString(),
                    person.get("lastnameMother").toString(),
                    person.get("numBI").toString(),
                    person.get("numNIF").toString(),
                    person.get("numSNS").toString(),
                    person.get("numSS").toString(),
                    person.get("cardNumber").toString(),
                    person.get("cardNumberPAN").toString(),
                    person.get("cardVersion").toString(),
                    person.get("nationality").toString(),
                    person.get("country").toString(),
                    person.get("documentType").toString(),
                    person.get("deliveryDate").toString(),
                    person.get("deliveryEntity").toString(),
                    person.get("locale").toString(),
                    person.get("mrz1").toString(),
                    person.get("mrz2").toString(),
                    person.get("mrz3").toString(),
                    person.get("notes").toString(),
                    ""
            );*/
            db.connect(databasePath);

            //db.dump_interaction(card, card_interaction.getRoomCode(), card_interaction.getInteraction());
            db.dump_rabbit_interaction(rabbit_person);
            if(rabbit_person.getInteraction().equals("inserted"))
                db.update_curent_card(rabbit_person.getPerson_id());
            else
                db.update_curent_card("1");
            
            db.connection_close();

            System.out.println("interaction saved to database");

        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(Simple_receiver.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }
}
