package com.akdev.devconnect.devconnect.dto;

public class SearchFilteredResultDTO {
    private Long id;
    private String userName;
    private String bio;
    private String profilePicture;


    public SearchFilteredResultDTO(Long id, String userName, String bio, String profilePicture) {
        this.id = id;
        this.userName = userName;
        this.bio = bio;
        this.profilePicture = profilePicture;
    }

    public SearchFilteredResultDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        userName = userName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
