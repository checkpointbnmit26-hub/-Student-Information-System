package com.studentsystem.server.controller;

import com.studentsystem.server.dto.CourseRequest;
import com.studentsystem.server.dto.ResponseWrapper;
import com.studentsystem.server.model.Course;
import com.studentsystem.server.model.Enrollment;
import com.studentsystem.server.model.User;
import com.studentsystem.server.repository.EnrollmentRepository;
import com.studentsystem.server.repository.UserRepository;
import com.studentsystem.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.studentsystem.server.model.Enrollment;
import com.studentsystem.server.repository.EnrollmentRepository;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // GET /api/v1/courses/count - Get the number of courses
    @GetMapping("/count")
    public ResponseEntity<ResponseWrapper<Long>> getCourseCount() {
        long count = courseService.getCourseCount();
        return ResponseEntity.ok(new ResponseWrapper<>(count));
    }

    // POST /api/v1/courses/{courseId}/assign/{studentId} - Assign a course to a student
    @PostMapping("/{courseId}/assign/{studentId}")
    public ResponseEntity<ResponseWrapper<String>> assignCourseToStudent(@PathVariable UUID courseId, @PathVariable UUID studentId) {
        try {
            courseService.assignCourseToStudent(courseId, studentId);
            return ResponseEntity.ok(new ResponseWrapper<>("Course assigned to student successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ResponseWrapper<>(e.getMessage()));
        }
    }

    // ============================================
    // PUBLIC ENDPOINTS (Anyone can access)
    // ============================================

    /**
     * GET /api/v1/courses
     * Anyone can view all available courses
     */
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Course>>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(new ResponseWrapper<>(courses));
    }

    // ============================================
    // ADMIN-ONLY ENDPOINTS
    // ============================================

    /**
     * POST /api/v1/courses
     * Admin only - Create a new course
     */
    @PostMapping
    public ResponseEntity<ResponseWrapper<Course>> createCourse(@RequestBody CourseRequest courseRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminEmail = authentication.getName();
        User admin = userRepository.findByEmail(adminEmail);

        if (admin == null) {
            return ResponseEntity.status(401).build();
        }

        Course savedCourse = courseService.createCourse(courseRequest, admin);
        return ResponseEntity.status(201).body(new ResponseWrapper<>(savedCourse));
    }

    /**
     * PUT /api/v1/courses/{id}
     * Admin only - Update an existing course
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable UUID id,
            @RequestBody CourseRequest courseRequest) {
        try {
            Course updatedCourse = courseService.updateCourse(id, courseRequest);
            return ResponseEntity.ok(new ResponseWrapper<>(updatedCourse));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ResponseWrapper<>("Course not found"));
        }
    }

    /**
     * DELETE /api/v1/courses/{id}
     * Admin only - Delete a course
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteCourse(@PathVariable UUID id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok(new ResponseWrapper<>("Course deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ResponseWrapper<>("Course not found"));
        }
    }

    /**
     * GET /api/v1/courses/{courseId}/enrollments
     * Admin only - View all students enrolled in a specific course with their details
     */
    @GetMapping("/{courseId}/enrollments")
    public ResponseEntity<?> getCourseEnrollments(@PathVariable UUID courseId) {
        try {
            List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
            
            // Build response with course info and student details
            Map<String, Object> response = Map.of(
                "courseId", courseId,
                "totalEnrolled", enrollments.size(),
                "students", enrollments.stream().map(e -> Map.of(
                    "studentId", e.getStudent().getId(),
                    "studentName", e.getStudent().getName(),
                    "studentEmail", e.getStudent().getEmail(),
                    "enrollmentStatus", e.getStatus(),
                    "requestDate", e.getRequestDate(),
                    "approvedDate", e.getApprovedDate()
                )).toList()
            );
            
            return ResponseEntity.ok(new ResponseWrapper<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ResponseWrapper<>("Course not found"));
        }
    }

    /**
     * GET /api/v1/courses/{courseId}/enrolled-students
     * Admin only - Alternative endpoint name for getting enrolled students
     * This is an alias for getCourseEnrollments to support both endpoint names
     */
    @GetMapping("/{courseId}/enrolled-students")
    public ResponseEntity<?> getCourseEnrolledStudents(@PathVariable UUID courseId) {
        return getCourseEnrollments(courseId);
    }
}