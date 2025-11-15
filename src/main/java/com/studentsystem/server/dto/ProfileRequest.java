package com.studentsystem.server.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProfileRequest {
    // These are the fields a student can edit
    private String bio;
    private Integer enrollmentYear;

    // These fields are likely set by an admin,
    // but we'll include them for completeness.
    // A better design might be a separate "AdminProfileRequest".
    private BigDecimal gpa;
    private Integer totalCredits;
}