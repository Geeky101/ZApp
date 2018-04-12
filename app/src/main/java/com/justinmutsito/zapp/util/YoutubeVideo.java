package com.justinmutsito.zapp.util;

/**
 * Created by justin on 12/10/17.
 */

public class YoutubeVideo {

    private String title;
    private String id;
    private String thumbnailUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUrl() {
        return "https://youtube.com/watch?v=" + getId();
    }

}
