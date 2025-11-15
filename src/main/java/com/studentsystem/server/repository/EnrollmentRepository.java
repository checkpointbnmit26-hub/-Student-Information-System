package com.studentsystem.server.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.studentsystem.server.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    
    // Find all enrollments for a specific student
    List<Enrollment> findByStudentId(UUID studentId);
    
    // Find all enrollments for a specific course
    List<Enrollment> findByCourseId(UUID courseId);
}