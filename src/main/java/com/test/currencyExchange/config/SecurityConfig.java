package com.test.currencyExchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF (enable it if needed)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated() // Secure API endpoints
                        .anyRequest().permitAll() // Allow other requests
                )
                .httpBasic(Customizer.withDefaults()); // Use Basic Authentication

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // In-memory authentication with a user "user" and password "password"
        String encodedPassword = passwordEncoder().encode("123"); // Encode the password

        return username -> User
                .withUsername("user") // Username
                .password(encodedPassword) // Encoded password
                .roles("USER") // User role
                .build();
    }
}

