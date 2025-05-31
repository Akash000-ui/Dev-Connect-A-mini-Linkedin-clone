package com.akdev.devconnect.devconnect.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "devConnect_seq1")
    @SequenceGenerator(name = "devConnect_seq1", sequenceName = "postsModel_seq", allocationSize = 1)
    private Long id;

    private String title;
    private String content;
    private String imageUrl;
    private String authorName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersModel author;

    @OneToMany(mappedBy = "post_comment", cascade = CascadeType.ALL)
    List<Comments> comments;

    @OneToMany(mappedBy = "post_like", cascade = CascadeType.ALL)
    List<Likes> likes;

    @OneToMany(mappedBy = "post_dislike", cascade = CascadeType.ALL)
    List<DisLikes> dislikes;

    @OneToMany(mappedBy = "postNotification", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    public List<DisLikes> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<DisLikes> dislikes) {
        this.dislikes = dislikes;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public UsersModel getAuthor() {
        return author;
    }

    public void setAuthor(UsersModel author) {
        this.author = author;
    }
}
