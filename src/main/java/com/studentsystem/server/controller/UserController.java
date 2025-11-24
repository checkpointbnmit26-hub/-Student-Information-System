package com.studentsystem.server.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.studentsystem.server.dto.ProfileRequest;
import com.studentsystem.server.dto.ResponseWrapper;
import com.studentsystem.server.dto.UserResponse;
import com.studentsystem.server.model.StudentProfile;
import com.studentsystem.server.model.User;
import com.studentsystem.server.repository.UserRepository;
import com.studentsystem.server.service.UserService; // Make sure this is imported

/**
 * CRITICAL UPDATE:
 * All endpoints are now wrapped with ResponseWrapper as requested.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired // THIS WAS THE MISSING LINE
    private UserService userService; 
    
    @Autowired
    private UserRepository userRepository;

    // Helper to get the currently logged-in user
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); 
        return userRepository.findByEmail(userEmail);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<UserResponse>> getMyProfile() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(404).build();
        }
        // Return user info (not profile)
        UserResponse userResponse = new UserResponse();
        userResponse.setId(currentUser.getId());
        userResponse.setName(currentUser.getName());
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setRole(currentUser.getRole());
        userResponse.setPhone(currentUser.getPhone());
        userResponse.setAddress(currentUser.getAddress());
        return ResponseEntity.ok(new ResponseWrapper<>(userResponse));
    }

    @PostMapping("/me/profile")
    public ResponseEntity<ResponseWrapper<StudentProfile>> createOrUpdateMyProfile(@RequestBody ProfileRequest profileRequest) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(401).build();
        }
        StudentProfile savedProfile = userService.createOrUpdateProfile(currentUser, profileRequest);
        // Wrap the response
        return ResponseEntity.ok(new ResponseWrapper<>(savedProfile));
    }

    @GetMapping("/me/profile")
    public ResponseEntity<ResponseWrapper<StudentProfile>> getMyStudentProfile() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(404).build();
        }
        StudentProfile profile = userService.getUserProfile(currentUser.getId());
        if (profile == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(new ResponseWrapper<>(profile));
    }

    @GetMapping("/all")
   
    public ResponseEntity<ResponseWrapper<List<UserResponse>>> getAllUsers() {
        // This line will now work
        List<UserResponse> users = userService.getAllUsers(); 
        // Wrap the response
        return ResponseEntity.ok(new ResponseWrapper<>(users));
    }
}