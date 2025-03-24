package com.util.financialbackend.security;

import com.util.financialbackend.security.DTO.JWTFilter;


import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {
    @Value("${roles.user}")
    String[] userRole;
    String maximumAuth = "ADMIN";
    List<String> ignoredSecurityList = List.of(
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/security/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/index.html",
            "/h2-console/**");

    @Bean
    public SecurityFilterChain chainFilter(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf ->{
                    ignoredSecurityList.forEach( ignored->{
                        csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher(ignored));
                    });
                })
                .headers(header->header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( request ->{
                    //spent
                    request.requestMatchers(AntPathRequestMatcher.antMatcher("/v1/spent/query")).hasAnyRole(userRole);
                    request.requestMatchers(AntPathRequestMatcher.antMatcher("/v1/spent/get")).hasAnyRole(userRole);
                    request.requestMatchers(AntPathRequestMatcher.antMatcher("/v1/spent/list")).hasAnyRole(maximumAuth);
                    request.requestMatchers(AntPathRequestMatcher.antMatcher("/v1/spent/update")).hasAnyRole(userRole);
                    request.requestMatchers(AntPathRequestMatcher.antMatcher("/v1/spent/delete")).hasAnyRole(userRole);
                    //customer
                    request.requestMatchers(AntPathRequestMatcher.antMatcher("/v1/customer/add/spent")).hasAnyRole(userRole);
                    request.requestMatchers(AntPathRequestMatcher.antMatcher("/v1/customer/get")).hasAnyRole(userRole);
                    request.requestMatchers(AntPathRequestMatcher.antMatcher("/v1/customer/list")).hasAnyRole(maximumAuth);
                    request.requestMatchers(AntPathRequestMatcher.antMatcher("/v1/customer/save")).permitAll();
                    request.requestMatchers(AntPathRequestMatcher.antMatcher("/login")).permitAll();
                    ignoredSecurityList.forEach(ignored->{
                        request.requestMatchers(AntPathRequestMatcher.antMatcher(ignored)).permitAll();
                    });
                    request.anyRequest().hasRole(maximumAuth);
                }).build();
    }
}
