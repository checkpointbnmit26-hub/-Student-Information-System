package com.studentsystem.server.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.studentsystem.server.model.CourseMaterial;

public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, UUID> {
    
    // Find all materials for a specific course
    List<CourseMaterial> findByCourseId(UUID courseId);
}