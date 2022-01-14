package my.demo.exploration.libs.spring.service.mvc.datajparest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

/* The @SpringBootApplication is a single annotation equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan. 
 * @Configuration: Tags the class as a source of bean definitions for the application context. Includes the @Component annotation.
 * @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
 * 				For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet.
 * @ComponentScan: Tells Spring to look for other components, configurations, and services in the my.demo.exploration package, letting it find the controllers. */
@SpringBootApplication
/* The lombok's @Slf4j annotation is equivalent to the logger definition for this class.
 * 				private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class) */
@Slf4j
public class AccessingDataJpaWithRestApplication {

	public static void main(String[] args) {
		/* Launch an application. */
		SpringApplication.run(AccessingDataJpaWithRestApplication.class, args);
	}
	
	/** Demo is a method that executes few tests of the CustomerRepository class.
	 * 
	 * @param repository
	 * @return a CommandLineRunner bean that automatically runs the code when the application launches.
	 */
	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");
		};
	}
}