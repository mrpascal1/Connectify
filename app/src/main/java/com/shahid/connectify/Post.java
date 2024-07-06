package com.shahid.connectify;

import java.util.HashMap;

public class Post {
    private String username, postId;
    private String timestamp;
    private String description;
    private String title;

    private String imageUrl;
    private HashMap<String, Object> likes;


    public Post(String postId, String username, String timestamp, String description, String title, String imageUrl, HashMap<String, Object> likes) {
        this.postId = postId;
        this.username = username;
        this.timestamp = timestamp;
        this.description = description;
        this.title = title;
        this.imageUrl = imageUrl;
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public HashMap<String, Object> getLikes() {
        return likes;
    }

    public void setLikes(HashMap<String, Object> likes) {
        this.likes = likes;
    }
}