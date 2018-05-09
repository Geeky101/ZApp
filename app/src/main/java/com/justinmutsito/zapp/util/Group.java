package com.justinmutsito.zapp.util;

public class Group {
    private String name, avatarUrl, unread;

    public Group() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public Group(String name, String avatarUrl, String unreadTexts) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.unread = unreadTexts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
    }
}
