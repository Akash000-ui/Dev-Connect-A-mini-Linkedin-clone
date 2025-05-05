package com.akdev.devconnect.devconnect.dto;


public class LikesAndDislikes {
    private Long likesCount;
    private Long dislikesCount;
    private Long postId;
    private Long userId;

    public LikesAndDislikes() {
    }
    public LikesAndDislikes(Long likesCount, Long dislikesCount , Long postId, Long userId) {
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.postId = postId;
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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
}
