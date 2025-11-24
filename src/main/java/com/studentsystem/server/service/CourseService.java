package com.studentsystem.server.service;

import com.studentsystem.server.dto.CourseRequest;
import com.studentsystem.server.model.Course;
import com.studentsystem.server.model.User;
import com.studentsystem.server.repository.CourseRepository;
import com.studentsystem.server.model.Enrollment;
import com.studentsystem.server.repository.EnrollmentRepository;
import com.studentsystem.server.repository.UserRepository;
import com.studentsystem.server.model.enums.EnrollmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new course.
     * @param request The DTO with course data.
     * @param admin The admin user who is creating this course.
     * @return The newly saved Course.
     */
    public Course createCourse(CourseRequest request, User admin) {
        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setStartDate(request.getStartDate());
        course.setEndDate(request.getEndDate());
        course.setCapacity(request.getCapacity() != null ? request.getCapacity() : 50);
        course.setEnrolledCount(0);
        course.setCreatedBy(admin); // Link to the admin who created it

        return courseRepository.save(course);
    }

    /**
     * Gets a list of all courses.
     * @return List of all courses.
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Updates an existing course.
     * @param courseId The ID of the course to update.
     * @param request The DTO with updated course data.
     * @return The updated Course.
     */
    public Course updateCourse(UUID courseId, CourseRequest request) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setStartDate(request.getStartDate());
        course.setEndDate(request.getEndDate());
        if (request.getCapacity() != null) {
            course.setCapacity(request.getCapacity());
        }
        
        return courseRepository.save(course);
    }

    /**
     * Deletes a course by its ID.
     * @param courseId The ID of the course to delete.
     */
    public void deleteCourse(UUID courseId) {
        // We should add checks here to see if the course exists,
        // but for now, this is the basic operation.
        courseRepository.deleteById(courseId);
    }

    /**
     * Returns the total number of courses.
     */
    public long getCourseCount() {
        return courseRepository.count();
    }

    /**
     * Assigns a course to a student (creates an enrollment).
     */
    public void assignCourseToStudent(UUID courseId, UUID studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check if already enrolled
        boolean alreadyEnrolled = enrollmentRepository.findByCourseId(courseId).stream()
                .anyMatch(e -> e.getStudent().getId().equals(studentId));
        if (alreadyEnrolled) {
            throw new RuntimeException("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setStatus(EnrollmentStatus.APPROVED);
        enrollment.setRequestDate(java.time.OffsetDateTime.now());
        enrollment.setApprovedDate(java.time.OffsetDateTime.now());
        enrollmentRepository.save(enrollment);

        // Update enrolled count
        course.setEnrolledCount(course.getEnrolledCount() + 1);
        courseRepository.save(course);
    }
}