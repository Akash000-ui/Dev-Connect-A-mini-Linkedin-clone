package com.akdev.devconnect.devconnect.dto;

import java.util.List;

public class PostWithCommentsDisLikesAndLikesDTO {
    private Long postId;
    private String authorName;
    private String title;
    private String content;
    private String imageUrl;
    private Long likesCount;
    private Long dislikesCount;
    private List<CommentDTO> comments;

    public PostWithCommentsDisLikesAndLikesDTO(Long postId, String title, String content, String imageUrl, Long likesCount, Long dislikesCount, List<CommentDTO> comments) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.comments = comments;
    }
    public PostWithCommentsDisLikesAndLikesDTO() {
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
}
