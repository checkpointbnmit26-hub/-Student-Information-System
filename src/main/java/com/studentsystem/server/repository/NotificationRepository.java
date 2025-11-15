package com.studentsystem.server.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.studentsystem.server.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    
    // Find all notifications for a specific user
    List<Notification> findByUserId(UUID userId);
}