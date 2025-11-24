package com.studentsystem.server.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors; // 1. IMPORT THIS
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentsystem.server.dto.ProfileRequest;
import com.studentsystem.server.dto.UserResponse; // 2. IMPORT THIS
import com.studentsystem.server.model.StudentProfile;
import com.studentsystem.server.model.User;
import com.studentsystem.server.repository.StudentProfileRepository;
import com.studentsystem.server.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    /**
     * Gets the profile for a specific user.
     * @param userId The ID of the user.
     * @return The user's profile, or null if not found.
     */
    public StudentProfile getUserProfile(UUID userId) {
        // We find the profile by the user's ID, not the profile's ID
        return studentProfileRepository.findByUserId(userId);
    }
    
    /**
     * Gets a user by their ID.
     * @param userId The ID of the user.
     * @return The User object.
     */
    public User getUserById(UUID userId) {
        // findById returns an Optional, so we use .orElse(null)
        return userRepository.findById(userId).orElse(null);
    }

    /**
     * Creates a new profile or updates an existing one for a user.
     * @param user The user to create/update the profile for.
     * @param request The DTO with the new profile data.
     * @return The saved StudentProfile.
     */
    public StudentProfile createOrUpdateProfile(User user, ProfileRequest request) {
        
        // 1. Check if a profile already exists
        StudentProfile profile = studentProfileRepository.findByUserId(user.getId());

        // 2. If it doesn't exist, create a new one
        if (profile == null) {
            profile = new StudentProfile();
            profile.setUser(user); // Link the profile to the user
        }

        // 3. Update the fields from the request
        if (request.getBio() != null) {
            profile.setBio(request.getBio());
        }
        if (request.getEnrollmentYear() != null) {
            profile.setEnrollmentYear(request.getEnrollmentYear());
        }
        if (request.getGpa() != null) {
            profile.setGpa(request.getGpa());
        }
        if (request.getTotalCredits() != null) {
            profile.setTotalCredits(request.getTotalCredits());
        }

        // 4. Save the new or updated profile to the database
        return studentProfileRepository.save(profile);
    }

    // --- THIS IS THE MISSING METHOD THAT WILL FIX YOUR ERROR ---
    /**
     * Gets all users and converts them to a safe DTO.
     * @return A list of UserResponse objects.
     */
    public List<UserResponse> getAllUsers() {
        // 1. Fetch all users from the database
        List<User> users = userRepository.findAll();

        // 2. Convert (or "map") each User object to a UserResponse DTO
        return users.stream()
            .map(user -> {
                UserResponse dto = new UserResponse();
                dto.setId(user.getId());
                dto.setName(user.getName());
                dto.setEmail(user.getEmail());
                dto.setRole(user.getRole());
                dto.setPhone(user.getPhone());
                dto.setAddress(user.getAddress());
                return dto;
            })
            .collect(Collectors.toList());
    }
}