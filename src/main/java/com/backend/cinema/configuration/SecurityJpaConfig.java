package com.backend.cinema.configuration;

import com.backend.cinema.services.security.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Profile("mysql")
public class SecurityJpaConfig {

	private final JpaUserDetailsService userDetailsService;

	public SecurityJpaConfig(JpaUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests(auth -> auth.antMatchers("/broadcasts").permitAll()
				.antMatchers("/auction").hasAnyRole().antMatchers("/broadcasts/**").hasRole("ADMIN")
				.antMatchers("/auction").hasAnyRole().antMatchers("/movies/**").hasRole("ADMIN")
				.antMatchers("/movies/add").hasRole("ADMIN").antMatchers("/login").permitAll()
				.antMatchers("/broadcasts/add").hasRole("ADMIN").antMatchers("/login").permitAll()
		// .anyRequest().authenticated()
		).userDetailsService(userDetailsService).formLogin().loginPage("/login").loginProcessingUrl("/perform_login")
				.and().exceptionHandling().accessDeniedPage("/access_denied").and().httpBasic(withDefaults()).build();
	}

}
