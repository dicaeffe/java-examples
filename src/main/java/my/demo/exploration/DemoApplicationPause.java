//package demo;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.event.KafkaEvent;
//
//@SpringBootApplication
//public class DemoApplicationPause implements ApplicationListener<KafkaEvent> {
//
//    public static void main(String[] args) {
//        SpringApplication.run(DemoApplicationPause.class, args).close();
//    }
//
//    @Override
//    public void onApplicationEvent(KafkaEvent event) {
//        System.out.println(event);
//    }
//
//    @Bean
//    public ApplicationRunner runner(KafkaListenerEndpointRegistry registry,
//            KafkaTemplate<String, String> template) {
//        return args -> {
//            template.send("pause.resume.topic", "thing1");
//            Thread.sleep(10_000);
//            System.out.println("pausing");
//            registry.getListenerContainer("pause.resume").pause();
//            Thread.sleep(10_000);
//            template.send("pause.resume.topic", "thing2");
//            Thread.sleep(10_000);
//            System.out.println("resuming");
//            registry.getListenerContainer("pause.resume").resume();
//            Thread.sleep(10_000);
//        };
//    }
//
//    @KafkaListener(id = "pause.resume", topics = "pause.resume.topic")
//    public void listen(String in) {
//        System.out.println(in);
//    }
//
//    @Bean
//    public NewTopic topic() {
//        return new NewTopic("pause.resume.topic", 2, (short) 1);
//    }
//
//}