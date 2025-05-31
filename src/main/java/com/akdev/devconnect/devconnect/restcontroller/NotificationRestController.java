package com.akdev.devconnect.devconnect.restcontroller;


import com.akdev.devconnect.devconnect.dto.NotificationDTO;
import com.akdev.devconnect.devconnect.model.Notification;
import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.NotificationRepo;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import com.akdev.devconnect.devconnect.services.NotificationService;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostsRepo postsRepo;

    @PostMapping("/sendMessage")
    public ResponseEntity<Object> sendMessage(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam (required = false) Long postId,
            @RequestParam String message
    ){

        System.out.println("In sendMessage method");
        System.out.println("Sender ID: " + senderId);
        System.out.println("Receiver ID: " + receiverId);
        System.out.println("Message: " + message);
        Optional<UsersModel> sender = userRepo.findById(senderId);
        Optional<UsersModel> receiver = userRepo.findById(receiverId);
        Optional<Posts> post = Optional.empty();
        if(postId != null){
            post = postsRepo.findById(postId);
        }
        if (sender.isEmpty() || receiver.isEmpty()) {
            return ResponseEntity.badRequest().body("Sender or receiver not found");
        }

        System.out.println("Taking help of notification service");

            notificationService.sendMessage(sender.get(), receiver.get(), post.get() ,message);

        System.out.println("Message sent successfully");
        HashMap<String , String> response = new HashMap<>();
        response.put("message", "Message sent successfully");
        return ResponseEntity.ok(response);
        
    }

    @GetMapping("/get-unread-notifications/{userId}")
    public ResponseEntity<Object> getUnreadNotifications(@PathVariable Long userId) {
        UsersModel user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        List<Notification> unreadNotifications = notificationRepo.findByReceiverIdAndStatus(userId, Notification.NotificationStatus.UNREAD);
        if (unreadNotifications.isEmpty()) {
            return ResponseEntity.ok("No unread notifications");
        } else {
            List<NotificationDTO> notificationsDTO = new ArrayList<>();
            for (int i = 0; i < unreadNotifications.size(); i++) {
                NotificationDTO notificationDTO = new NotificationDTO();
                Notification notification = unreadNotifications.get(i);
                notificationDTO.setId(notification.getId());
                notificationDTO.setMessage(notification.getMessage());
                notificationDTO.setSenderId(notification.getSender().getId());
                notificationDTO.setReceiverId(notification.getReceiver().getId());
                notificationDTO.setStatus(notification.getStatus().toString());
                notificationDTO.setCreatedAt(notification.getCreatedAt().toString());
                notificationDTO.setSenderName(notification.getSender().getName());
                notificationDTO.setReceiverName(notification.getReceiver().getName());
                if (notification.getPostNotification() != null) {
                    notificationDTO.setPostId(notification.getPostNotification().getId().toString());
                    notificationDTO.setPostTitle(notification.getPostNotification().getTitle());
                }
                notificationDTO.setSenderProfileImage(notification.getSender().getProfileImageUrl());
                System.out.println("Notification DTO created: " + notificationDTO);

                notificationsDTO.add(notificationDTO);

            }
            HashMap<String  , Object> response = new HashMap<>();
            response.put("unreadNotifications", notificationsDTO);
            response.put("message", "Unread notifications retrieved successfully");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<Object> markAsRead(@PathVariable Long notificationId) {
        Notification notification = notificationRepo.findById(notificationId).orElse(null);
        if (notification == null) {
            return ResponseEntity.badRequest().body("Notification not found");
        }

        notification.setStatus(Notification.NotificationStatus.READ);
        notificationRepo.save(notification);
        return ResponseEntity.ok("Notification marked as read");
    }
}

