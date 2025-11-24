package com.studentsystem.server.dto;

import java.time.LocalDate;
import lombok.Data;

// This is the JSON object the admin will send to create or update a course.
@Data
public class CourseRequest {

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer capacity;

}