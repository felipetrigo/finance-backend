package com.util.financialbackend.security;

import com.util.financialbackend.security.DTO.JWTFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${roles.user}")
    String[] userRole;
    String maximumAuth = "ADMIN";

    @Bean
    public void chainFilter(HttpSecurity http) throws Exception {
        http.addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( request ->{
                    //spent
                    request.requestMatchers("/v1/spent/query").hasAnyRole(userRole);
                    request.requestMatchers("/v1/spent/get").hasAnyRole(userRole);
                    request.requestMatchers("/v1/spent/list").hasAnyRole(maximumAuth);
                    request.requestMatchers("/v1/spent/update").hasAnyRole(userRole);
                    request.requestMatchers("/v1/spent/delete").hasAnyRole(userRole);
                    //customer
                    request.requestMatchers("/v1/customer/add/spent").hasAnyRole(userRole);
                    request.requestMatchers("/v1/customer/get").hasAnyRole(userRole);
                    request.requestMatchers("/v1/customer/list").hasAnyRole(maximumAuth);
                    request.requestMatchers("/v1/customer/save").permitAll();
                    request.requestMatchers("/login").permitAll();
                    request.anyRequest().hasRole(maximumAuth);
                });
    }
}
