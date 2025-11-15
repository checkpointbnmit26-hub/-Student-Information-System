package com.studentsystem.server.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.studentsystem.server.model.User;

// We extend JpaRepository, telling it two things:
// 1. This repository is for our "User" model.
// 2. The ID type of our "User" model is "UUID".
public interface UserRepository extends JpaRepository<User, UUID> {
    
    // Spring Data JPA is smart. 
    // It will automatically create a query for a method named like this:
    // "Find a User by their email"
    User findByEmail(String email);
    
}