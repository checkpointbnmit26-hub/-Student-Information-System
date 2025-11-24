package com.studentsystem.server.dto;

import lombok.Data;

/**
 * A generic wrapper for all API responses, as requested by the frontend team.
 * This ensures a consistent response format: { "data": ... }
 */
@Data
public class ResponseWrapper<T> {
    private T data;

    public ResponseWrapper(T data) {
        this.data = data;
    }
}