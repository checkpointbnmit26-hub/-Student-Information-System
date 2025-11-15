package com.studentsystem.server.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class AuthResponse {
    private String token; // The JWT token for authentication
    private UUID userId;
    private String name;
    private String role;
}