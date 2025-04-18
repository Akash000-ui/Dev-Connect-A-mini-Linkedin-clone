package com.akdev.devconnect.devconnect.model;

import jakarta.persistence.*;

@Entity
public class DisLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "devConnect_seq4")
    @SequenceGenerator(name = "devConnect_seq4", sequenceName = "dislikeModel_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UsersModel user_dislike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post_dislike;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersModel getUser_dislike() {
        return user_dislike;
    }

    public void setUser_dislike(UsersModel user_dislike) {
        this.user_dislike = user_dislike;
    }

    public Posts getPost_dislike() {
        return post_dislike;
    }

    public void setPost_dislike(Posts post_dislike) {
        this.post_dislike = post_dislike;
    }
}
