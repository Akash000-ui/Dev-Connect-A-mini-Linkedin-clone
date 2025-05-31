package com.akdev.devconnect.devconnect.dto;

public class CommentDTO {
    private Long commentId;
    private Long userId;
    private String content;
    private String author;
    private String profileImageUrl;

    public CommentDTO(Long commentId, String content, String author , String profileImageUrl , Long userId) {
        this.commentId = commentId;
        this.content = content;
        this.author = author;
        this.profileImageUrl = profileImageUrl;
        this.userId = userId;
    }
    public CommentDTO() {
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;

    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
