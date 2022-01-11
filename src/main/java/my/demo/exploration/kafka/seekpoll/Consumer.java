package demo.kafka.seekpoll;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@NoArgsConstructor
public class Consumer {
	
//	@Value("${myconf.kafka.topic-format}")
	private String topicFormat = "%s.demoTest.123";
	
	@Value("${myconf.kafka.partition-backward-offset}")
	private long partitionBackwardOffset;
	
	@Value("${myconf.kafka.maxPollTimeout}")
	private long maxPollTimeout;
	
	public ConsumerRecords<String, String> consume(String tenant, String userId) {

		//Configuration of Kafka connection
		Map<String, Object> kafkaConfigMap = new HashMap<>();
		//Kafka Server
		kafkaConfigMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 			"kafka01:9092");
		//Set how to serialize key/value pairs
		kafkaConfigMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 		StringDeserializer.class.getName());
		kafkaConfigMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 		StringDeserializer.class.getName());
		//Only retry after one second.
//		kafkaConfig.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 				kafka.getRetryBackoffMs())
		//Request timeout - request.timeout.ms
//		kafkaConfig.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 				kafka.getRequestTimeoutMs())
		//Batch up to 64K buffer sizes.
//		kafkaConfig.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, 			kafka.getReconnectBackoffMs())
//		kafkaConfig.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 		kafka.getReconnectBackoffMaxMs()
		kafkaConfigMap.put(ConsumerConfig.GROUP_ID_CONFIG, 						"demo_kafka");
		kafkaConfigMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 				"512");
		kafkaConfigMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,				"earliest");
		kafkaConfigMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,			false);
		
		//Instantiate a consumer with the provided configurations
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaConfigMap);

		String topic = String.format(topicFormat, tenant);
		ConsumerRecords<String, String> records = null;
		
		try {
			//Subscribe the Topic
			log.info("Subscribing to topic: [{}]", topic);
			consumer.subscribe(Arrays.asList(topic));
			
			Set<TopicPartition> assignedPartitions = new HashSet<>();
			//repeat until Kafka has assigned one or more Partitions to the consumer
			while (assignedPartitions.isEmpty()) {
				log.info("Execute poll");
				//poll action: fetch data about Partitions of the subscribed Topic
				consumer.poll(Duration.ofMillis(100));
				//Store Partitions assigned to the consumer
				assignedPartitions = consumer.assignment();
			}
			log.info("For topic [{}] has been assigned {} Partitions: {}", topic, assignedPartitions.size(), assignedPartitions);
			
			//go to last offset for each partition
			consumer.seekToEnd(assignedPartitions);
			//for each Partition assigned execute the seek method: this will reset the offset to the position provided
			for (TopicPartition topicPartiton : assignedPartitions) {
				
				//current end offset of the partition
				long endPosition = consumer.position(topicPartiton);
				
				//found the offset for the last "n" messages, where "n"=backwardOffset
				long recentMessagesOffset;
				if (endPosition - partitionBackwardOffset > 0) {
					recentMessagesOffset = endPosition - partitionBackwardOffset;
				} else {
					recentMessagesOffset = 0;
				}
				log.info("For topic [{}] move offset from {} to {}", topicPartiton, endPosition, recentMessagesOffset);
				
				//set the offset
				consumer.seek(topicPartiton, recentMessagesOffset);
			}
			
			boolean repeatLoop = true;
			long start = System.currentTimeMillis();
			while (repeatLoop || records == null) {
				//Poll all the records to be consumed according to the offset set in the seek operation
				records = consumer.poll(Duration.ofMillis(100));
				log.info("Records polled are [{}]", records.count());
				
				for (ConsumerRecord<String, String> record : records) {
					log.info("Partition: {}, Record offset {} : {}", record.partition(), record.offset(), record.value());
					
					//if the element has been found, exit from the while loop
					log.info("===> Message found!");
					repeatLoop = false;
				}
				
				//if the element has not
				if (repeatLoop && (System.currentTimeMillis() - start) > maxPollTimeout) {
					log.info("===> Blocking poll for timeout: [{} > {}]", (System.currentTimeMillis() - start), maxPollTimeout);
					repeatLoop = false;
				}
			}
			log.debug("End of While");
			
		} catch (Exception e) {
			log.error("#### -> Error [{}]", e);
			
		} finally {
			log.info("Closing Kafka consumer");
			consumer.unsubscribe();
			deleteTopic(topic);
			consumer.close();
			log.debug("Closed Kafka consumer");
		}
		return records;
	}
   
	/** Execute the deletion of a topic.
	 * 
	 * @param topic
	 */
    private void deleteTopic(String topic) {
        log.info("Deleting Kafka topic: {}", topic);
        
        Properties prop = new Properties();
        prop.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "kafka01:9092");
        AdminClient adminClient = AdminClient.create(prop);
        
        try {
            adminClient.deleteTopics(Collections.singleton(topic)).all().get();
           
            log.info("Kafka topic deleted: [{}]", topic);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
			
		} finally {
			log.info("Closing Kafka consumer");
			adminClient.close();
			log.debug("Closed Kafka consumer");
        }
    }
}