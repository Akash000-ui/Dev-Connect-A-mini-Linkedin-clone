package com.akdev.devconnect.devconnect.dto;

public class CommentDTO {
    private Long commentId;
    private String content;
    private String author;

    public CommentDTO(Long commentId, String content, String author) {
        this.commentId = commentId;
        this.content = content;
        this.author = author;
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
}
