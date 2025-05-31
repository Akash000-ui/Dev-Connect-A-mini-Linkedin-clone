package com.akdev.devconnect.devconnect.dto;

import java.util.List;

public class PostWithCommentsDisLikesAndLikesDTO {
    private Long postId;
    private Long userId;
    private String profileImageUrl;
    private String authorName;
    private String title;
    private String content;
    private String imageUrl;
    private Long likesCount;
    private Long dislikesCount;
    private Long clientId;
    private List<CommentDTO> comments;

    public PostWithCommentsDisLikesAndLikesDTO(Long userId ,Long postId, String title, String content, String imageUrl, Long likesCount, Long dislikesCount, List<CommentDTO> comments , Long clientId , String profileImageUrl) {
        this.postId = postId;
        this.title = title;
        this.userId = userId;
        this.content = content;
        this.imageUrl = imageUrl;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.comments = comments;
        this.clientId = clientId;
        this.profileImageUrl = profileImageUrl;
    }
    public PostWithCommentsDisLikesAndLikesDTO() {
    }

    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }


    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public Long getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(Long dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
