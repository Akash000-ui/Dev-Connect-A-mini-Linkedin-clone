package com.akdev.devconnect.devconnect.dto;

import org.springframework.web.multipart.MultipartFile;

public class PostDTO {
    private String title;
    private String content;
    private MultipartFile image;
    private String authorName;
    private String authorId;

    public PostDTO(String title, String content, MultipartFile image, String authorName, String authorId) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.authorName = authorName;
        this.authorId = authorId;
    }
    public  PostDTO(){}

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
