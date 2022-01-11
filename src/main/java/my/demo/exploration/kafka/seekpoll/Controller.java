package demo.kafka.seekpoll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/kafka/topic/seekpoll")
@Slf4j
public class Controller {
	
    private final Producer producer;
    private final Consumer consumer;

    @Autowired
    Controller(Producer producer, Consumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }
    
    @PostMapping(value = "")
    public void sendMessageToKafkaTopic(@RequestParam("topic") String topic,
    									@RequestParam("message") String message) {
        this.producer.sendMessage(topic, message);
    }
    
    @GetMapping(value = "")
    public String getKafkaMessageBySeekPoll(@RequestParam() String tenant,
    										@RequestParam(defaultValue = "demoKafkaOK_16") String userId) {
    	log.info("========> START CONSUME | T: {} | uid: {}", tenant, userId);
    	consumer.consume(tenant, userId);
    	log.info("========> END CONSUME");
        return "done";
    }
}