/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Card_Reader;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rofler
 */
public class RPCClient {

    private Connection connection;
    private Channel channel;
    private String requestQueueName;
    private String replyQueueName;
    private QueueingConsumer consumer;
    private String hostIP;

    public RPCClient(String host, String requestQueueName) {
        this.requestQueueName = requestQueueName;
        this.hostIP = host;
    }

    public int openConnection() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setConnectionTimeout(3000);
            factory.setHandshakeTimeout(3000);
            factory.setHost(hostIP);
            connection = factory.newConnection();
            channel = connection.createChannel();
            
            replyQueueName = channel.queueDeclare().getQueue();
            consumer = new QueueingConsumer(channel);
            channel.basicConsume(replyQueueName, true, consumer);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(RPCClient.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } catch (TimeoutException ex) {
            System.err.println("rabbit server connection handshake timed out - will not send data");
            return -1;
        }
    }

    public String call(String message) {
        String response = null;
        try {
            String corrId = UUID.randomUUID().toString();

            BasicProperties props = new BasicProperties.Builder()
                    .correlationId(corrId)
                    .replyTo(replyQueueName)
                    .build();

            channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    response = new String(delivery.getBody(), "UTF-8");
                    break;
                }
            }
            

        } catch (IOException ex) {
            Logger.getLogger(RPCClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RPCClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ShutdownSignalException ex) {
            Logger.getLogger(RPCClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConsumerCancelledException ex) {
            Logger.getLogger(RPCClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public void close() {
        try {
            connection.close();
        } catch (IOException ex) {
            Logger.getLogger(RPCClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
