package com.studentsystem.server.model;

import java.math.BigDecimal; // For handling precise decimals like GPA
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne; // This is the new annotation
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    // This creates the one-to-one link. 
    // It's the "owner" of the relationship.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT") // Good practice for TEXT type
    private String bio;

    // We must define precision and scale to match NUMERIC(3,2)
    @Column(precision = 3, scale = 2) 
    private BigDecimal gpa = new BigDecimal("0.00"); // Set default

    @Column(name = "total_credits")
    private Integer totalCredits = 0; // Set default

    @Column(name = "enrollment_year")
    private Integer enrollmentYear;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}