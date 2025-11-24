package com.studentsystem.server.dto;

import lombok.Data;

/**
 * CRITICAL UPDATE:
 * This DTO now matches exactly what your frontend code expects:
 * { "token": "...", "user": { ... } }
 */
@Data
public class AuthResponse {
    private String token;
    private UserResponse user; // A nested user object

    public AuthResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }
}