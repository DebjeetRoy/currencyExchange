package com.test.currencyExchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // In-memory authentication with a user "user" and password "password"
        UserDetails admin = User.builder().username("dev")
                .password(passwordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder().username("raj")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated() // Secure API endpoints
                        .requestMatchers("/actuator/**").permitAll() // Allow all users to access Actuator endpoints
                        .requestMatchers(HttpMethod.POST).hasRole("ADMIN") // Restrict POST to ADMIN role
                        .requestMatchers(HttpMethod.GET).hasRole("USER") // Restrict GET to USER role
                        .anyRequest().authenticated()) // All other requests require authentication
                .httpBasic(withDefaults()) // Use basic authentication
                .csrf(AbstractHttpConfigurer::disable).build(); // Disable CSRF protection
    }
}

