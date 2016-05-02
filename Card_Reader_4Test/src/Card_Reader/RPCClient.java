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
 * Class used to communicate with rabbitMQ broker (deprecated: only for a
 * circumstance where the broker replies to messages)
 *
 * @author rofler
 */
public class RPCClient {

    /**
     * connection object
     */
    private Connection connection;

    /**
     * channel object
     */
    private Channel channel;

    /**
     * name of queue used for requests
     */
    private String requestQueueName;

    /**
     * name of queue used for replies
     */
    private String replyQueueName;

    /**
     * consumer of queue
     */
    private QueueingConsumer consumer;

    /**
     * IP address of broker
     */
    private String hostIP;

    /**
     * class constructor
     *
     * @param host IP of host
     * @param requestQueueName request queue name
     */
    public RPCClient(String host, String requestQueueName) {
        this.requestQueueName = requestQueueName;
        this.hostIP = host;
    }

    /**
     * establish connection to host
     *
     * @return 0 if success !0 if fail
     */
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

    /**
     * send message to host
     *
     * @param message body of message
     * @return response from host
     */
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

    /**
     * close connection to host
     */
    public void close() {
        try {
            connection.close();
        } catch (IOException ex) {
            Logger.getLogger(RPCClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
