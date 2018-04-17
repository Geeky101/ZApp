package com.justinmutsito.zapp.util;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.justinmutsito.zapp.keys.Keys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by justin on 12/10/17.
 */

public class YoutubeSearch {
    private YouTube mYouTube;
    private YouTube.Search.List mQuery;
    private Context mContext;

    private static final String TAG = "YoutubeSearch";
    private static final long NUMBER_OF_VIDEOS_RETURNED = 50;


    public YoutubeSearch(Context context) {
        mContext = context;
        mYouTube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {
            }
        }).setApplicationName("ZApp").build();

        try {
            mQuery = mYouTube.search().list("id,snippet");
            mQuery.setKey(Keys.YOUTUBE_DATA_API_KEY);
            mQuery.setType("video");
            mQuery.setOrder("date");
            mQuery.setChannelId(Keys.YOUTUBE_CHANNEL_ID);
            mQuery.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            mQuery.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        } catch (IOException e) {
            Log.d(TAG, "Could not initialize: " + e);
        }
    }

    public ArrayList<YoutubeVideo> search() {

        try {
            SearchListResponse response = mQuery.execute();
            List<SearchResult> results = response.getItems();

            ArrayList<YoutubeVideo> videos = new ArrayList<>();
            for (SearchResult result : results) {
                YoutubeVideo video = new YoutubeVideo();
                video.setTitle(result.getSnippet().getTitle());
                video.setThumbnailUrl(result.getSnippet().getThumbnails().getDefault().getUrl());
                video.setId(result.getId().getVideoId());
                videos.add(video);
            }
            return videos;
        } catch (IOException e) {
            Log.d(TAG, "Could not search: " + e);
            return null;
        }

    }



}
