package com.example.datban.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // tạm thời tắt CSRF cho Android client
            .authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/giohang/datmon").permitAll()
    .requestMatchers("/api/**", "/nhahang/**", "/uploads/**").permitAll()
    .anyRequest().authenticated()
)

            .formLogin().disable(); // tắt form login
        return http.build();
    }
}
