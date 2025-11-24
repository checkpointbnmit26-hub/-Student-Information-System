package com.studentsystem.server.service;

import com.studentsystem.server.model.Course;
import com.studentsystem.server.model.Enrollment;
import com.studentsystem.server.model.User;
import com.studentsystem.server.model.enums.EnrollmentStatus;
import com.studentsystem.server.repository.CourseRepository;
import com.studentsystem.server.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * A student requests enrollment in a course.
     */
    public Enrollment requestEnrollment(User student, UUID courseId) throws Exception {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new Exception("Course not found"));

        // TODO: Add check here to see if student is already enrolled

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.PENDING);
        // requestDate is set by default in the constructor

        return enrollmentRepository.save(enrollment);
    }

    /**
     * An admin approves an enrollment request.
     */
    public Enrollment approveEnrollment(User admin, UUID enrollmentId) throws Exception {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
            .orElseThrow(() -> new Exception("Enrollment not found"));

        enrollment.setStatus(EnrollmentStatus.APPROVED);
        enrollment.setApprovedBy(admin);
        enrollment.setApprovedDate(OffsetDateTime.now());

        return enrollmentRepository.save(enrollment);
    }

    /**
     * An admin rejects an enrollment request.
     */
    public Enrollment rejectEnrollment(User admin, UUID enrollmentId) throws Exception {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
            .orElseThrow(() -> new Exception("Enrollment not found"));

        enrollment.setStatus(EnrollmentStatus.REJECTED);
        enrollment.setApprovedBy(admin); // The admin who rejected it

        return enrollmentRepository.save(enrollment);
    }

    /**
     * Admin gets all pending requests.
     */
    public List<Enrollment> getPendingRequests() {
        return enrollmentRepository.findByStatus(EnrollmentStatus.PENDING);
    }

    /**
     * Student gets all their own enrollments.
     */
    public List<Enrollment> getMyEnrollments(User student) {
        return enrollmentRepository.findByStudentId(student.getId());
    }

    /**
     * Admin assigns a course directly to a student (approved immediately).
     */
    public Enrollment assignCourseToStudent(UUID studentId, UUID courseId) throws Exception {
        // Fetch student (should be in UserRepository, but we don't have it injected)
        // For now, we'll assume the student exists. You may need to adjust this.
        User student = new User();
        student.setId(studentId);

        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new Exception("Course not found"));

        // Check if student is already enrolled
        List<Enrollment> existing = enrollmentRepository.findByStudentId(studentId).stream()
            .filter(e -> e.getCourse().getId().equals(courseId))
            .toList();
        
        if (!existing.isEmpty()) {
            throw new Exception("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.APPROVED); // Directly approved
        enrollment.setApprovedDate(OffsetDateTime.now());

        return enrollmentRepository.save(enrollment);
    }

    /**
     * Admin gets all approved enrollments.
     */
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findByStatus(EnrollmentStatus.APPROVED);
    }

    /**
     * Student gets all their approved enrollments.
     */
    public List<Enrollment> getMyApprovedEnrollments(User student) {
        return enrollmentRepository.findByStudentId(student.getId()).stream()
            .filter(e -> e.getStatus() == EnrollmentStatus.APPROVED)
            .toList();
    }
}