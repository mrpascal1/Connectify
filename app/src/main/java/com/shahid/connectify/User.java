package com.shahid.connectify;

public class User {
    private String userId, Username, EmailID, imageUrl, bio;

    public User() {

    }
    public User(String userId, String Username, String emailID, String imageUrl, String bio) {
        this.userId = userId;
        this.Username = Username;
        EmailID = emailID;
        this.imageUrl = imageUrl;
        this.bio = bio;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
