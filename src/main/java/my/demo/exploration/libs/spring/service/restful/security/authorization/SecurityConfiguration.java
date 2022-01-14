package my.demo.exploration.libs.spring.service.restful.security.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/** WebSecurityConfig ensures that only authenticated users can see the secret greeting.
 * By default, all requests require authentication. */

@Configuration
/* The @EnableWebSecurity annotation enables the Spring Security's web secutiry support and provide the Spring MVC integration. */
@EnableWebSecurity
/* The SecurityConfiguration class extends WebSecurityConfigurerAdapter class in order to override a couple of its methods to set some specifics of the web security configuration. */
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	/** The configure(HttpSecurity) method defines which URL paths should be secured and which should not.
	 * Specifically, the "/" and "/home" paths are configured to not require any authentication.
	 * All other paths must be authenticated. */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/contact/**", "/contact**", "/").permitAll()
				.anyRequest().authenticated()
				.and();
	}

	/** The userDetailsService() method sets up an in-memory user store with a single user.. */
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		
		/* Create a user with name of "user", a password of "password", and a role of "USER". */
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}