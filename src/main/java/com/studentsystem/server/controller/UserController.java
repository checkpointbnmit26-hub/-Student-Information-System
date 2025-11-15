package com.studentsystem.server.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Import POST
import org.springframework.web.bind.annotation.RequestBody; // Import RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentsystem.server.dto.ProfileRequest; // Import the new DTO
import com.studentsystem.server.model.StudentProfile;
import com.studentsystem.server.model.User;
import com.studentsystem.server.repository.UserRepository;
import com.studentsystem.server.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository; 

    /**
     * GET /api/v1/users/me
     * Gets the profile of the currently authenticated user.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); 

        User currentUser = userRepository.findByEmail(userEmail);
        if (currentUser == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        StudentProfile profile = userService.getUserProfile(currentUser.getId());
        if (profile == null) {
            return ResponseEntity.status(404).body("Profile not found. Please create one.");
        }

        return ResponseEntity.ok(profile);
    }

    // --- NEW ENDPOINT ---
    /**
     * POST /api/v1/users/me/profile
     * Creates or updates the profile for the currently logged-in user.
     */
    @PostMapping("/me/profile")
    public ResponseEntity<?> createOrUpdateMyProfile(@RequestBody ProfileRequest profileRequest) {
        
        // 1. Get the current user (same as before)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); 
        User currentUser = userRepository.findByEmail(userEmail);
        
        if (currentUser == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        // 2. Call the service to create or update the profile
        try {
            StudentProfile savedProfile = userService.createOrUpdateProfile(currentUser, profileRequest);
            return ResponseEntity.ok(savedProfile); // Return the saved profile
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating profile: " + e.getMessage());
        }
    }
}