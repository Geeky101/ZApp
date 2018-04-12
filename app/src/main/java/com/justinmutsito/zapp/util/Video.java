package com.justinmutsito.zapp.util;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by justin on 04/12/18.
 */

public class Video {

    private String artist, title;
    private long id;
    private int duration;
    private Bitmap thumbnail;
    private Uri uri;

    public Video(String artist, String title, long id, int duration, Bitmap thumbnail, Uri uri) {
        this.artist = artist;
        this.title = title;
        this.id = id;
        this.duration = duration;
        this.thumbnail = thumbnail;
        this.uri = uri;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTime() {

        int minutes = getDuration() / 60000;
        int seconds = (getDuration() % 60000) / 1000;

        if (seconds < 10) {
            return minutes + ":0" + seconds;
        } else {
            return minutes + ":" + seconds;
        }

    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
