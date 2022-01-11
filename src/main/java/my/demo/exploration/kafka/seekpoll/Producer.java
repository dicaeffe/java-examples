package demo.kafka.seekpoll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//https://www.confluent.io/blog/apache-kafka-spring-boot-application/
@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
    	try {
        	String key = "myKey";
            logger.info(String.format("#### -> Producee Topic [%s] Message [%s]", topic, message));
            this.kafkaTemplate.send(topic, key, message);
            
		} catch (Exception e) {
            logger.error("#### -> Error [%s]", e);
		}
    }
}