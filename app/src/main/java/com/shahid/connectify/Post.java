package com.shahid.connectify;

public class Post {
    private String username;
    private String timestamp;
    private String description;
    private String title;
    private int imageResId;
    private int likes;


    public Post(String username, String timestamp, String description, String title, int imageResId, int likes) {
        this.username = username;
        this.timestamp = timestamp;
        this.description = description;
        this.title = title;
        this.imageResId = imageResId;
        this.likes = likes;
    }

    public Post() {

    }


    public String getUsername() {
        return username;
    }

    public String getTimestamp() {
        return timestamp;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}