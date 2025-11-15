package com.studentsystem.server.model;

import java.time.OffsetDateTime;
import java.util.UUID; // Import UUID

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator; // For generating UUIDs

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users") // Matches your friend's table name
public class User {

    @Id
    @GeneratedValue // This will use the default (UUID) generator
    @UuidGenerator // Specifically tells Hibernate to generate a UUID
    private UUID id;

    @Column(unique = true, nullable = false) // Matches UNIQUE, NOT NULL
    private String email;

    @Column(name = "password_hash", nullable = false) // Matches column name and NOT NULL
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role; // 'student' or 'admin'

    private String phone;
    private String address;

    @CreationTimestamp // Automatically sets this when a new user is created
    @Column(name = "created_at", updatable = false, nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp // Automatically updates this every time the user is modified
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}