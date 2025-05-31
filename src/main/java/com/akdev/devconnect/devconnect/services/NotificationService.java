package com.akdev.devconnect.devconnect.services;

import com.akdev.devconnect.devconnect.dto.NotificationDTO;
import com.akdev.devconnect.devconnect.model.Notification;
import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.NotificationRepo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(UsersModel sender, UsersModel receiver, Posts posts, String message) {

        System.out.println("Inside sendMessage method of NotificationService");
        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setMessage(message);
        notification.setStatus(Notification.NotificationStatus.UNREAD);
        notification.setCreatedAt(LocalDateTime.now());
        if (posts != null) {
            notification.setPostNotification(posts);
        }
        notificationRepo.save(notification);
        System.out.println("Notification saved to the database: " + notification);
        System.out.println("Creating NotificationDTO");

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setSenderId(notification.getSender().getId());
        notificationDTO.setSenderName(notification.getSender().getName());
        notificationDTO.setReceiverName(notification.getReceiver().getName());
        notificationDTO.setReceiverId(notification.getReceiver().getId());
        notificationDTO.setStatus(notification.getStatus().toString());
        notificationDTO.setCreatedAt(notification.getCreatedAt().toString());
        if (posts != null) {
            notificationDTO.setPostId(posts.getId().toString());
            notificationDTO.setPostTitle(posts.getTitle());
        }
        System.out.println("Notification DTO created: " + notificationDTO);

        System.out.println("Sending notification to the receiver");
        simpMessagingTemplate.convertAndSendToUser(
                receiver.getId().toString(),
                "/topic/notifications",
                notificationDTO
        );

//        simpMessagingTemplate.convertAndSend(
//                "/topic/notifications",
//                notificationDTO
//        );


        System.out.println("Notification sent to the receiver" + receiver.getId());
    }
}
