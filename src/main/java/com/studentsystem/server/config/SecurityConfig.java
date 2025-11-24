package com.studentsystem.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import com.studentsystem.server.filter.JwtAuthenticationFilter;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            
            .authorizeHttpRequests(auth -> auth
                // 1. Public Endpoints (No authentication needed)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                .requestMatchers("/api/v1/auth/**").permitAll()
                
                // 2. Course Access Rules
                // ✓ ANYONE can VIEW courses (no login needed)
                .requestMatchers(HttpMethod.GET, "/api/v1/courses").permitAll()
                // ✓ Only ADMINS can CREATE, UPDATE, or DELETE courses
                .requestMatchers(HttpMethod.POST, "/api/v1/courses").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/**").hasAuthority("ROLE_ADMIN")
                // ✓ Admins can view students enrolled in a course and course statistics
                .requestMatchers(HttpMethod.GET, "/api/v1/courses/*/enrollments").hasAuthority("ROLE_ADMIN")

                // 3. User Management (Admin Only)
                .requestMatchers(HttpMethod.GET, "/api/v1/users/all").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/enrolled").hasAuthority("ROLE_ADMIN")

                // 4. Enrollment Management
                // Admins can view all enrollments and pending requests
                .requestMatchers(HttpMethod.GET, "/api/v1/enrollments/all").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/enrollments/pending").hasAuthority("ROLE_ADMIN")
                // Admins can approve/reject enrollments
                .requestMatchers(HttpMethod.POST, "/api/v1/enrollments/approve").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/enrollments/reject").hasAuthority("ROLE_ADMIN")
                // Admins can assign courses to students
                .requestMatchers(HttpMethod.POST, "/api/v1/enrollments/assign").hasAuthority("ROLE_ADMIN")
                // Students can request enrollment
                .requestMatchers(HttpMethod.POST, "/api/v1/enrollments/request").hasAuthority("ROLE_STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/v1/enrollments/my-enrollments").hasAuthority("ROLE_STUDENT")
                
                // 5. Everything else requires login
                .anyRequest().authenticated()
            )
            
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}