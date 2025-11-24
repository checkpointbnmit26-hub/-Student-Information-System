package com.studentsystem.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studentsystem.server.dto.AuthResponse;
import com.studentsystem.server.dto.LoginRequest;
import com.studentsystem.server.dto.RegisterRequest;
import com.studentsystem.server.dto.UserResponse;
import com.studentsystem.server.model.User;
import com.studentsystem.server.repository.UserRepository;
import com.studentsystem.server.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Registers a new user.
     */
    /**
     * Registers a new user.
     */
    public User register(RegisterRequest request) {
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        
        
        String role = request.getRole();
        if (role != null) {
            newUser.setRole(role.toUpperCase()); 
        } else {
            newUser.setRole("STUDENT"); // Default to STUDENT if null
        }
        
        newUser.setPhone(request.getPhone());
        newUser.setAddress(request.getAddress());

        return userRepository.save(newUser);
    }

    /**
     * Authenticates a user and returns a JWT.
     */
    public AuthResponse login(LoginRequest request) {
        // This line checks the password
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        // Password was correct, find the user
        User user = userRepository.findByEmail(request.getEmail());

        // Create the "safe" user DTO
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setPhone(user.getPhone());
        userResponse.setAddress(user.getAddress());

        // Generate a token
        String token = jwtUtil.generateToken(user);

        // Return the new AuthResponse object
        return new AuthResponse(token, userResponse);
    }
    
}