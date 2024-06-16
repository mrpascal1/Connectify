package com.shahid.connectify;

public class Post {
    private String username;
    private String date;
    private String description;
    private int imageResId;
    private int likes;

    public Post(String username, String date, String description, int imageResId, int likes) {
        this.username = username;
        this.date = date;
        this.description = description;
        this.imageResId = imageResId;
        this.likes = likes;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getLikes() {
        return likes;
    }
}