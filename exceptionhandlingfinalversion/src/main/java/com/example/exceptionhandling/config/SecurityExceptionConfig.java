package com.example.exceptionhandling.config;

import com.example.exceptionhandling.filter.RequestIdFilter;
import com.example.exceptionhandling.handler.CustomAccessDeniedHandler;
import com.example.exceptionhandling.handler.CustomAuthenticationEntryPoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration.
 *
 * For JWT applications, your JWT filter will normally
 * also be added here.
 */
@Configuration
public class SecurityExceptionConfig {

	@Bean
	SecurityFilterChain securityFilterChain(
	        HttpSecurity http,
	        CustomAuthenticationEntryPoint authenticationEntryPoint,
	        CustomAccessDeniedHandler accessDeniedHandler,
	        RequestIdFilter requestIdFilter
	) throws Exception {

	    http
	        .csrf(csrf -> csrf.disable())

	        // Make sure RequestIdFilter executes
	        // before Spring Security authentication.
	        .addFilterBefore(
	                requestIdFilter,
	                UsernamePasswordAuthenticationFilter.class
	        )

	        .authorizeHttpRequests(auth -> auth

	            .requestMatchers("/auth/**")
	            .permitAll()

	            .requestMatchers("/admin/**")
	            .hasRole("ADMIN")
	            
	            .requestMatchers("/students/**")
	            .permitAll()

	            .anyRequest()
	            .authenticated()
	        )

	        .exceptionHandling(exception -> exception

	            .authenticationEntryPoint(
	                    authenticationEntryPoint
	            )

	            .accessDeniedHandler(
	                    accessDeniedHandler
	            )
	        );

	    return http.build();
	}
	
}
