package com.akdev.devconnect.devconnect.model;

import jakarta.persistence.*;

@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "devConnect_seq3")
    @SequenceGenerator(name = "devConnect_seq3", sequenceName = "likesModel_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UsersModel user_like;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post_like;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersModel getUser_like() {
        return user_like;
    }

    public void setUser_like(UsersModel user_like) {
        this.user_like = user_like;
    }

    public Posts getPost_like() {
        return post_like;
    }

    public void setPost_like(Posts post_like) {
        this.post_like = post_like;
    }
}
