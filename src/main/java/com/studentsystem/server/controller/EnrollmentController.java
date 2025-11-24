package com.studentsystem.server.controller;

import com.studentsystem.server.dto.ResponseWrapper;
import com.studentsystem.server.model.Enrollment;
import com.studentsystem.server.model.User;
import com.studentsystem.server.repository.UserRepository;
import com.studentsystem.server.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private UserRepository userRepository;

    // Helper to get the currently logged-in user
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); 
        return userRepository.findByEmail(userEmail);
    }

    /**
     * POST /api/v1/enrollments/request
     * A student requests enrollment in a course.
     */
    @PostMapping("/request")
   
    public ResponseEntity<ResponseWrapper<Enrollment>> requestEnrollment(@RequestBody Map<String, String> body) {
        try {
            User student = getCurrentUser();
            UUID courseId = UUID.fromString(body.get("courseId"));
            Enrollment enrollment = enrollmentService.requestEnrollment(student, courseId);
            return ResponseEntity.status(201).body(new ResponseWrapper<>(enrollment));
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * GET /api/v1/enrollments/my-requests
     * A student gets their list of enrollments (pending or approved).
     */
    @GetMapping("/my-requests")
   
    public ResponseEntity<ResponseWrapper<List<Enrollment>>> getMyEnrollments() {
        User student = getCurrentUser();
        List<Enrollment> enrollments = enrollmentService.getMyEnrollments(student);
        return ResponseEntity.ok(new ResponseWrapper<>(enrollments));
    }

    /**
     * GET /api/v1/enrollments/pending
     * An admin gets all pending requests.
     */
    @GetMapping("/pending")
   
    public ResponseEntity<ResponseWrapper<List<Enrollment>>> getPendingRequests() {
        List<Enrollment> enrollments = enrollmentService.getPendingRequests();
        return ResponseEntity.ok(new ResponseWrapper<>(enrollments));
    }

    /**
     * POST /api/v1/enrollments/approve
     * An admin approves a request.
     */
    @PostMapping("/approve")
   
    public ResponseEntity<ResponseWrapper<Enrollment>> approveRequest(@RequestBody Map<String, String> body) {
        try {
            User admin = getCurrentUser();
            UUID enrollmentId = UUID.fromString(body.get("enrollmentId"));
            Enrollment enrollment = enrollmentService.approveEnrollment(admin, enrollmentId);
            return ResponseEntity.ok(new ResponseWrapper<>(enrollment));
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * POST /api/v1/enrollments/reject
     * An admin rejects a request.
     */
    @PostMapping("/reject")
  
    public ResponseEntity<ResponseWrapper<Enrollment>> rejectRequest(@RequestBody Map<String, String> body) {
        try {
            User admin = getCurrentUser();
            UUID enrollmentId = UUID.fromString(body.get("enrollmentId"));
            Enrollment enrollment = enrollmentService.rejectEnrollment(admin, enrollmentId);
            return ResponseEntity.ok(new ResponseWrapper<>(enrollment));
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * POST /api/v1/enrollments/assign
     * An admin directly assigns a course to a student (no approval needed)
     */
    @PostMapping("/assign")
    public ResponseEntity<?> assignCourseToStudent(@RequestBody Map<String, String> body) {
        try {
            UUID studentId = UUID.fromString(body.get("studentId"));
            UUID courseId = UUID.fromString(body.get("courseId"));
            Enrollment enrollment = enrollmentService.assignCourseToStudent(studentId, courseId);
            return ResponseEntity.status(201).body(new ResponseWrapper<>(enrollment));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ResponseWrapper<>(e.getMessage()));
        }
    }

    /**
     * GET /api/v1/enrollments/all
     * An admin views all enrollments (approved ones)
     */
    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<List<Enrollment>>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(new ResponseWrapper<>(enrollments));
    }

    /**
     * GET /api/v1/enrollments/my-enrollments
     * A student gets their list of enrollments (approved ones).
     */
    @GetMapping("/my-enrollments")
    public ResponseEntity<ResponseWrapper<List<Enrollment>>> getMyApprovedEnrollments() {
        User student = getCurrentUser();
        List<Enrollment> enrollments = enrollmentService.getMyApprovedEnrollments(student);
        return ResponseEntity.ok(new ResponseWrapper<>(enrollments));
    }
}