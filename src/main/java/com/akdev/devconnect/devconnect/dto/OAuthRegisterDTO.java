package com.akdev.devconnect.devconnect.dto;

import java.util.List;

public class OAuthRegisterDTO {

    private String token;
    private List<String> skills;
    private String bio;

    public OAuthRegisterDTO(String token, List<String> skills, String bio) {
        this.token = token;
        this.skills = skills;
        this.bio = bio;
    }
    public OAuthRegisterDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
