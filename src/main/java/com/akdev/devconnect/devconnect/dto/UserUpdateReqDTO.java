package com.akdev.devconnect.devconnect.dto;

import java.util.List;

public class UserUpdateReqDTO {
    private String name;
    private String email;
    private String bio;
    private List<String> skills;
    private String imageUrl;

    public UserUpdateReqDTO() {
    }
    public UserUpdateReqDTO(String name, String email, String bio, List<String> skills, String imageUrl) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.skills = skills;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
