package com.studentsystem.server.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated; // For the 'percentage' column
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    // --- Relationships (Foreign Keys) ---

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "graded_by", nullable = false)
    private User gradedBy;

    // --- Other Fields ---

    @Column(name = "assignment_name", nullable = false)
    private String assignmentName;

    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal score;

    @Column(name = "max_score", precision = 5, scale = 2, nullable = false)
    private BigDecimal maxScore = new BigDecimal("100.00"); // Set default

    // This tells Hibernate this column is calculated by the database
    @Generated
    @Column(precision = 5, scale = 2, insertable = false, updatable = false)
    private BigDecimal percentage;

    @Column(name = "graded_date")
    private OffsetDateTime gradedDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // --- Timestamps ---

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
    
    // Set default for graded_date
    public Grade() {
        this.gradedDate = OffsetDateTime.now();
    }
}