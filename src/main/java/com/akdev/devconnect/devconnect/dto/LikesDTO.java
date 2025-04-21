package com.akdev.devconnect.devconnect.dto;

public class LikesDTO {

    private Long user_like_id;
    private Long post_like_id;

    public LikesDTO() {
    }

    public LikesDTO( Long user_like_id, Long post_like_id) {
        this.user_like_id = user_like_id;
        this.post_like_id = post_like_id;
    }

    public Long getUser_like_id() {
        return user_like_id;
    }

    public void setUser_like_id(Long user_like_id) {
        this.user_like_id = user_like_id;
    }

    public Long getPost_like_id() {
        return post_like_id;
    }

    public void setPost_like_id(Long post_like_id) {
        this.post_like_id = post_like_id;
    }
}
