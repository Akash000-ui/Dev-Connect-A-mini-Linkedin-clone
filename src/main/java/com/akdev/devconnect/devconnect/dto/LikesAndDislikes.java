package com.akdev.devconnect.devconnect.dto;


public class LikesAndDislikes {
    private Long likesCount;
    private Long dislikesCount;

    public LikesAndDislikes() {
    }
    public LikesAndDislikes(Long likesCount, Long dislikesCount) {
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
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
