package com.studentsystem.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentsystem.server.dto.AuthResponse; // Import AuthResponse
import com.studentsystem.server.dto.LoginRequest; // Import LoginRequest
import com.studentsystem.server.dto.RegisterRequest;
import com.studentsystem.server.model.User;
import com.studentsystem.server.service.AuthService;

@RestController // Tells Spring this is a Controller that returns JSON
@RequestMapping("/api/v1/auth") // Makes all methods in this class start with this URL
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * POST /api/v1/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        
        try {
            User registeredUser = authService.register(registerRequest);
            return ResponseEntity.status(201).body("User registered successfully: " + registeredUser.getEmail());
        
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // --- NEW LOGIN ENDPOINT ---
    /**
     * POST /api/v1/auth/login
     * It expects a JSON body matching the LoginRequest DTO.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        
        try {
            // 1. Call our service to do the hard work
            AuthResponse authResponse = authService.login(loginRequest);
            
            // 2. If successful, return 200 OK with the AuthResponse (token, user info)
            return ResponseEntity.ok(authResponse);

        } catch (Exception e) {
            // If authentication fails (wrong password, user not found)
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }
}