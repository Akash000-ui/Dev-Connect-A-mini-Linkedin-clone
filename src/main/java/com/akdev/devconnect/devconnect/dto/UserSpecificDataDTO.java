package com.akdev.devconnect.devconnect.dto;

import java.util.List;

public class UserSpecificDataDTO {

    Long id;
    String name;
    String email;
    String profileImageUrl;
    String bio;
    List<String> skills;
    List<PostWithCommentsDisLikesAndLikesDTO> posts;

    public UserSpecificDataDTO(Long id, String name, String email, String profileImageUrl, String bio, List<String> skills, List<PostWithCommentsDisLikesAndLikesDTO> posts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.skills = skills;
        this.posts = posts;
    }

    public UserSpecificDataDTO() {
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

    public List<PostWithCommentsDisLikesAndLikesDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostWithCommentsDisLikesAndLikesDTO> posts) {
        this.posts = posts;
    }
}
