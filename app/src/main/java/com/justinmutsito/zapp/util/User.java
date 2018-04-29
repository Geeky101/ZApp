package com.justinmutsito.zapp.util;

public class User {
    private String username;
    private String status;
    private String avatarUrl;
    private String id;


    public User(String username, String status, String avatarUrl, String id) {
        this.username = username;
        this.status = status;
        this.avatarUrl = avatarUrl;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
