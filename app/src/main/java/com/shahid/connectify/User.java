package com.shahid.connectify;

public class User {
    private String userId, Username, EmailID, imageUrl;

    public User() {

    }
    public User(String userId, String Username, String emailID, String imageUrl) {
        this.userId = userId;
        this.Username = Username;
        EmailID = emailID;
        this.imageUrl = imageUrl;
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
}
