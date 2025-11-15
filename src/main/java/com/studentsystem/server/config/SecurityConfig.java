package com.studentsystem.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider; // Import this
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy; // Import this
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Import this

import com.studentsystem.server.filter.JwtAuthenticationFilter; // Import your new filter

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // --- NEW DEPENDENCIES ---
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    
    @Autowired
    private AuthenticationProvider authenticationProvider; // The bean from ApplicationConfig

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            
            // This is the important part: Authorization Rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll() // Allow login/register
                .anyRequest().authenticated() // Secure ALL other requests
            )
            
            // --- NEW CONFIGURATION ---
            
            // 1. Tell Spring to be "stateless." No sessions.
            //    This is required for a JWT-based API.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 2. Tell Spring to use our AuthenticationProvider (from ApplicationConfig)
            .authenticationProvider(authenticationProvider)
            
            // 3. Tell Spring to add our custom JWT filter *before* its standard
            //    username/password filter.
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}