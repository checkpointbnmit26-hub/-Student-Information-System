package com.studentsystem.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This NEW class provides a global CORS configuration for your application.
 * This will fix the "Invalid ID or password" error in your web app.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
            .allowedOrigins("*") // Allow all origins (Vercel, localhost, mobile)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Allow all methods
            .allowedHeaders("*"); // Allow all headers (like 'Authorization')
    }
}