package my.demo.exploration.libs.amqp.client.rabbitmq;
//package my.demo.exploration.amqp.client.rabbitmq;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class Sender {
//	
//	/** Define the queue name. */
//	private static final String QUEUE_NAME = "exploration";
//	
//	/** Define the hostname or IP address of RabbitMQ server. */
//	private static final String HOST = "localhost";
//	
//	
//	public static void main(String[] argv) throws Exception {
//		log.info("Attempt to send a message to RabbitMQ on '{}' queue", QUEUE_NAME);
//		
//		//Create a connection to the RabbitMQ server
//		ConnectionFactory factory = new ConnectionFactory();
//		factory.setHost(HOST);
//		/* n.b.: you can use try-with-resources statement because both Connection and Channel implement java.io.Closeable,
//		 * so this way you don't need to close them explicitly in our code. */
//		try (Connection connection = factory.newConnection();
//		     Channel channel = connection.createChannel()) {
//			log.info(" [x] Channel created");
//			
//			/* Declaring a queue is idempotent - it will only be created if it doesn't exist already.*/
//			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//			log.info(" [x] Declare queue");
//			
//			/* n.b.: The message content is a byte array, so you can encode whatever you like there.*/
//			String message = "Hello World!";
//			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//			log.info(" [x] Sent '{}' on '{}' queue", message, QUEUE_NAME);
//			
//		} catch (Exception e) {
//			log.error("The attempt to connect at RabbitMQ server has failed", e);
//		}
//	}
//}