package my.demo.exploration.libs.spring.service.mvc.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/** The WebConfig class defines a Java-based configuration of Spring beans. */
/* The @Configuration annotation is the main artifact used by the Java-based Spring configuration;
 * it is itself meta-annotated with @Component, which makes the annotated classes standard beans and as such, also candidates for component-scanning.
 * The main purpose of @Configuration classes is to be sources of bean definitions for the Spring IoC Container (IoC = Inversion of Control). See https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans */
@Configuration
/* Unlike the Spring Boot solution, you have to explicitly define @EnableWebMvc for setting up default Spring MVC Configurations.
The @EnableWebMvc annotation provides the Spring Web MVC configuration such as setting up the dispatcher servlet, 
enabling the @Controller and the @RequestMapping annotations and setting up other defaults. */
@EnableWebMvc
/* Unlike the Spring Boot solution, you have to explicitly define @ComponentScan to specify packages to scan for components.
 * @ComponentScan configures the component scanning directive, specifying the packages to scan. */
@ComponentScan(basePackages = "my.demo.exploration.spring.service.mvc.configuration")
public class WebConfig {

}
