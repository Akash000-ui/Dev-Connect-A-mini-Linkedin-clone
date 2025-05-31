package com.akdev.devconnect.devconnect.repositories;

import com.akdev.devconnect.devconnect.model.Notification;
import com.akdev.devconnect.devconnect.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification , Long> {
    List<Notification> findByReceiverIdAndStatus(Long id, Notification.NotificationStatus notificationStatus);
}

