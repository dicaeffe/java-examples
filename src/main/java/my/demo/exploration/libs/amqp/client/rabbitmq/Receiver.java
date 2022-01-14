package my.demo.exploration.libs.amqp.client.rabbitmq;
//package my.demo.exploration.amqp.client.rabbitmq;
//
//import java.nio.charset.StandardCharsets;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.DeliverCallback;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class Receiver {
//	
//	/** Define the queue name. */
//	private final static String QUEUE_NAME = "hello";
//	
//	/** Define the hostname or IP address of RabbitMQ server. */
//	private static final String HOST = "localhost";
//
//	public static void main(String[] argv) throws Exception {
//		log.info("Attempt to get any message from RabbitMQ on '{}' queue", QUEUE_NAME);
//		
//		//Create a connection to the RabbitMQ server
//		ConnectionFactory factory = new ConnectionFactory();
//		factory.setHost(HOST);
//		Connection connection = factory.newConnection();
//		Channel channel = connection.createChannel();
//		log.info(" [x] Channel created");
//		
//		/* Declaring a queue is idempotent - it will only be created if it doesn't exist already.*/
//		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//		log.info(" [x] Declare queue");
//		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//		
//		/* Since the server will push us messages asynchronously, 
//		 * we provide a callback in the form of an object that will buffer the messages until we're ready to use them.*/
//		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//            System.out.println(" [x] Received '" + message + "'");
//        };
//        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
//    }
//}