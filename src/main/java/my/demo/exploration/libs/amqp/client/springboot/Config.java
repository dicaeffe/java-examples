package my.demo.exploration.libs.amqp.client.springboot;
//package my.demo.exploration.amqp.client.springboot;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//@Profile({"tut1","hello-world"})
//@Configuration
//public class Config {
//
//    @Bean
//    public Queue hello() {
//        return new Queue("hello");
//    }
//
//    @Profile("receiver")
//    @Bean
//    public Receiver receiver() {
//        return new Receiver();
//    }
//
//    @Profile("sender")
//    @Bean
//    public Sender sender() {
//        return new Sender();
//    }
//}