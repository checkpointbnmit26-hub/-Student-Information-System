package com.studentsystem.server.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.studentsystem.server.model.StudentProfile;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, UUID> {
    
    // Find a profile by the user's ID
    StudentProfile findByUserId(UUID userId);
}