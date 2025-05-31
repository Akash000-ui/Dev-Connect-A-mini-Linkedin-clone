package com.akdev.devconnect.devconnect.model;

import jakarta.persistence.*;

@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "devConnect_seq2")
    @SequenceGenerator(name = "devConnect_seq2", sequenceName = "commentsModel_seq", allocationSize = 1)
    private Long id;

    private String content;
    private String  authorName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UsersModel comment_user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post_comment;

    private Long comment_user_id;
    private Long post_comment_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public UsersModel getComment_user() {
        return comment_user;
    }

    public void setComment_user(UsersModel comment_user) {
        this.comment_user = comment_user;
    }

    public Posts getPost_comment() {
        return post_comment;
    }

    public void setPost_comment(Posts post_comment) {
        this.post_comment = post_comment;
    }


    public Long getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(Long comment_user_id) {
        this.comment_user_id = comment_user_id;
    }

    public Long getPost_comment_id() {
        return post_comment_id;
    }

    public void setPost_comment_id(Long post_comment_id) {
        this.post_comment_id = post_comment_id;
    }
}
