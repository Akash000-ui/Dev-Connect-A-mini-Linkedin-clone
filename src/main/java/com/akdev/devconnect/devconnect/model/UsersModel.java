package com.akdev.devconnect.devconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "devConnect_seq")
    @SequenceGenerator(name = "devConnect_seq", sequenceName = "userModel_seq", allocationSize = 1)
     private Long id;

        @Column(nullable = false)
        private String name;
        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = true)
        private String password;

        private String profileImageUrl;

        private String bio;

        @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
        private List<Posts> posts;

        @OneToMany(mappedBy = "comment_user", cascade = CascadeType.ALL)
        private List<Comments> comments;

        @ElementCollection
        private List<String> skills;

        @OneToMany(mappedBy = "user_like", cascade = CascadeType.ALL)
        private List<Likes> likes;

        @OneToMany(mappedBy = "user_dislike", cascade = CascadeType.ALL)
        private List<DisLikes> dislikes;

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void toString(String name) {
        this.name = name;
    }
}
