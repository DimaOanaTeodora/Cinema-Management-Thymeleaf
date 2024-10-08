package com.backend.cinema.configuration;

import com.backend.cinema.services.security.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Profile("h2")
public class SecurityConfigH2 {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("guest").password(passwordEncoder.encode("12345")).roles("USER").build());
		manager.createUser(
				User.withUsername("admin").password(passwordEncoder.encode("12345")).roles("USER", "ADMIN").build());
		return manager;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
				.authorizeRequests(auth -> auth.antMatchers("/h2-console/**").permitAll().antMatchers("/products/**")
						.permitAll().anyRequest().authenticated())
				.headers(headers -> headers.frameOptions().sameOrigin()).httpBasic(withDefaults()).build();
	}

}
