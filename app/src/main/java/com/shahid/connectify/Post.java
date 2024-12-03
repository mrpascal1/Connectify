package com.shahid.connectify;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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

    public String getFormattedTimestamp() {
        long timestampLong = Long.parseLong(timestamp);
        Date date = new Date(timestampLong);

        // Custom date format
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

        String day = dayFormat.format(date);
        String suffix = getDayOfMonthSuffix(Integer.parseInt(day));

        return day + suffix + " " + monthYearFormat.format(date);
    }

    private String getDayOfMonthSuffix(int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
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
