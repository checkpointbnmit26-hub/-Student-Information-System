package com.studentsystem.server.dto;

import java.util.UUID;
import lombok.Data;

/**
 * This is a "safe" object to send user data.
 * Notice it has NO passwordHash field.
 */
@Data
public class UserResponse {
    private UUID id;
    private String email;
    private String name;
    private String role;
    private String phone;
    private String address;
}