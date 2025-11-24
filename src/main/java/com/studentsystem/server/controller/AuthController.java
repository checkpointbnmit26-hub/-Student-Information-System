package com.studentsystem.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentsystem.server.dto.AuthResponse;
import com.studentsystem.server.dto.LoginRequest;
import com.studentsystem.server.dto.RegisterRequest;
import com.studentsystem.server.dto.UserResponse;
import com.studentsystem.server.model.User;
import com.studentsystem.server.service.AuthService;

/**
 * CRITICAL UPDATE:
 * This controller now returns the EXACT format your frontend expects.
 * It does NOT use the ResponseWrapper, because your frontend code is
 * expecting the raw AuthResponse object.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        User registeredUser = authService.register(registerRequest);

        // Create the "safe" user DTO to return
        UserResponse userResponse = new UserResponse();
        userResponse.setId(registeredUser.getId());
        userResponse.setName(registeredUser.getName());
        userResponse.setEmail(registeredUser.getEmail());
        userResponse.setRole(registeredUser.getRole());
        userResponse.setPhone(registeredUser.getPhone());
        userResponse.setAddress(registeredUser.getAddress());

        return ResponseEntity.status(201).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // This returns the exact { "token": "...", "user": {...} } object
            AuthResponse authResponse = authService.login(loginRequest);
            return ResponseEntity.ok(authResponse);

        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}