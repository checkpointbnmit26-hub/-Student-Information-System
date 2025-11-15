package com.studentsystem.server.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Import the new DTO
import com.studentsystem.server.dto.ProfileRequest; 
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
     */
    public StudentProfile getUserProfile(UUID userId) {
        return studentProfileRepository.findByUserId(userId);
    }
    
    /**
     * Gets a user by their ID.
     */
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // --- NEW METHOD ---
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
}