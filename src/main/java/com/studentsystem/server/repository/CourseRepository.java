package com.studentsystem.server.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.studentsystem.server.model.Course;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    // We can add custom query methods here later if needed
}