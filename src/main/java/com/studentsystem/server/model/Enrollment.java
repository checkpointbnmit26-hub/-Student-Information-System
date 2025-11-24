package com.studentsystem.server.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.studentsystem.server.model.enums.EnrollmentStatus; // Import our new Enum

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
// This adds the UNIQUE(student_id, course_id) constraint from your schema
@Table(name = "enrollments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
public class Enrollment {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    // --- Relationships (Foreign Keys) ---

    // Link to the User (student)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // Link to the Course
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Link to the User (admin who approved)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by") // Nullable by default, which is correct
    private User approvedBy;

    // --- Other Fields ---

    @Enumerated(EnumType.STRING) // This tells Spring to save the status as a string ("PENDING", "APPROVED")
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.PENDING; // Sets the default value

    @Column(name = "request_date")
    private OffsetDateTime requestDate;

    @Column(name = "approved_date")
    private OffsetDateTime approvedDate;

    @Column(name = "enrolled_at")
    private OffsetDateTime enrolledAt;

    // --- Timestamps ---

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
    
    // We can set the request_date when the object is created
    public Enrollment() {
        this.requestDate = OffsetDateTime.now();
    }
}