package com.studentsystem.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studentsystem.server.dto.AuthResponse;
import com.studentsystem.server.dto.LoginRequest;
import com.studentsystem.server.dto.RegisterRequest;
import com.studentsystem.server.model.User;
import com.studentsystem.server.repository.UserRepository;
import com.studentsystem.server.util.JwtUtil; // Import JwtUtil

@Service // Tells Spring this is a Service class
public class AuthService {

    // --- Dependencies ---
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // --- NEW DEPENDENCIES FOR LOGIN ---
    @Autowired
    private JwtUtil jwtUtil; // Our token generator
    
    @Autowired
    private AuthenticationManager authenticationManager; // Our security manager

    /**
     * Registers a new user (student or admin).
     */
    public User register(RegisterRequest request) {
        
        // 1. Create a new User object from the request
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        
        // 2. Hash the password before saving!
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        
        newUser.setRole(request.getRole()); // "student" or "admin"
        newUser.setPhone(request.getPhone());
        newUser.setAddress(request.getAddress());

        // 3. Save the new user to the database
        return userRepository.save(newUser);
    }

    // --- NEW LOGIN METHOD ---
    /**
     * Authenticates a user and returns a JWT.
     * @param request The DTO containing login info.
     * @return An AuthResponse DTO with the token and user details.
     */
    public AuthResponse login(LoginRequest request) {
        // 1. Let Spring Security check the email and password
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        // If the line above doesn't throw an exception, the user is authenticated!

        // 2. Get the full User object from the database
        User user = userRepository.findByEmail(request.getEmail());

        // 3. Generate a JWT for this user
        String token = jwtUtil.generateToken(user);

        // 4. Create and return the AuthResponse
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setRole(user.getRole());
        
        return response;
    }
}