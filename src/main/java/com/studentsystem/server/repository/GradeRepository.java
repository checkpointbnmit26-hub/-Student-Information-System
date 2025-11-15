package com.studentsystem.server.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.studentsystem.server.model.Grade;

public interface GradeRepository extends JpaRepository<Grade, UUID> {
    
    // Find all grades for a specific enrollment
    List<Grade> findByEnrollmentId(UUID enrollmentId);
}