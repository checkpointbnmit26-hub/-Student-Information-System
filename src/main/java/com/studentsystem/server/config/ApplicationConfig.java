package com.studentsystem.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.studentsystem.server.repository.UserRepository;
import com.studentsystem.server.model.User; // Import your User model

import java.util.ArrayList; // For authorities list

@Configuration
public class ApplicationConfig {

    @Autowired
    private UserRepository userRepository;

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This is the bean Spring Security uses to find a user by their username (in our case, email).
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            // 1. Find the user in our database by their email
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }

            // 2. Convert our 'User' model into Spring Security's 'UserDetails'
            // We are using a simple authorities list for now.
            return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                new ArrayList<>() // Empty authorities list
            );
        };
    }

    /**
     * This bean is the "data provider" that uses our UserDetailsService and PasswordEncoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder()); // <-- Call the method directly (with parentheses)
        return authProvider;
    }

    /**
     * This is the main "manager" that Spring will use to authenticate a login request.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}