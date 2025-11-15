package com.studentsystem.server.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.studentsystem.server.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

@Component // Tells Spring to manage this class
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService; // This is the bean from ApplicationConfig

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, 
            @NonNull HttpServletResponse response, 
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get the "Authorization" header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Check if the header is missing or doesn't start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If so, pass the request to the next filter and exit
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the token (the part after "Bearer ")
        jwt = authHeader.substring(7);

        try {
            // 4. Validate the token
            if (jwtUtil.validateToken(jwt)) {
                
                // 5. Get the user's ID/email from the token
                // We're using email as the "username"
                userEmail = jwtUtil.parseToken(jwt).get("email", String.class); 
                
                // 6. Check if the user is not already authenticated
                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    
                    // 7. Load the user's details from our database
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                    
                    // 8. Create the authentication object
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // We don't need credentials
                        new ArrayList<>() // Empty authorities list for now
                    );
                    
                    // 9. Set the authenticated user in the Security Context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token is invalid (expired, wrong signature, etc.)
            // We just let the request continue without authentication.
            // Spring Security will block it later if the endpoint is protected.
        }

        // 10. Pass the request to the next filter
        filterChain.doFilter(request, response);
    }
    
    // Helper method to get user ID from token
    private UUID getUserIdFromToken(String token) {
        String subject = jwtUtil.parseToken(token).getSubject();
        return UUID.fromString(subject);
    }
}