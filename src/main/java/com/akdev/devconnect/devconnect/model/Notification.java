package com.akdev.devconnect.devconnect.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "devConnect_seq")
    @SequenceGenerator(name = "devConnect_seq", sequenceName = "userModel_seq", allocationSize = 1)
    private Long id;


    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UsersModel sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UsersModel receiver;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts postNotification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status = NotificationStatus.UNREAD;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UsersModel getSender() {
        return sender;
    }

    public void setSender(UsersModel sender) {
        this.sender = sender;
    }

    public UsersModel getReceiver() {
        return receiver;
    }

    public void setReceiver(UsersModel receiver) {
        this.receiver = receiver;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum NotificationStatus {
        READ,
        UNREAD
    }

    public Posts getPostNotification() {
        return postNotification;
    }

    public void setPostNotification(Posts postNotification) {
        this.postNotification = postNotification;
    }
}

