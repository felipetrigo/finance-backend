package com.util.financialbackend.security.config;


import com.util.financialbackend.security.service.SecurityFilter;
import com.util.financialbackend.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration{

    @Autowired
    private SecurityFilter securityFilter;
    String userRole = "USER";
    String maximumAuth = "ADMIN";
    List<String> ignoredSecurityList = List.of(
            "/api-docs/**",
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/swagger-config",
            "/favicon.ico",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/security/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/index.html",
            "/swagger-ui/**",
            "/h2-console/**",
            "/finance-solution/v1/users/login",
            "/v1/users/login",
            "/v1/customer/save"
    );


    @Bean
    public SecurityFilterChain chainFilter(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {

                    // Depois as rotas ignoradas
                    ignoredSecurityList.forEach(ignored -> {
                        request.requestMatchers(AntPathRequestMatcher.antMatcher(ignored)).permitAll();
                    });

                    // Depois as rotas específicas com roles
                    // spent
                    request
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/finance-solution/v1/spent/query")).hasAnyRole(userRole)
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/finance-solution/v1/spent/get")).hasAnyRole(userRole)
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/finance-solution/v1/spent/list")).hasAnyRole(maximumAuth)
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/finance-solution/v1/spent/update")).hasAnyRole(userRole)
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/finance-solution/v1/spent/delete")).hasAnyRole(userRole)
                    // customer
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/finance-solution/v1/customer/add/spent")).hasAnyRole(userRole)
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/finance-solution/v1/customer/get")).hasAnyRole(userRole)
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/finance-solution/v1/customer/list")).hasAnyRole(maximumAuth)

                    // Por último, qualquer outra requisição
                    .anyRequest().authenticated(); // ou denyAll() se preferir bloquear
                })
                .addFilterAfter(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsService(ClientService clientService) {
        return clientService::findByUsername;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
