package com.justinmutsito.zapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.DynamicConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.fragments.AudioBrowserFragment;
import com.justinmutsito.zapp.fragments.VideoBrowserFragment;
import com.justinmutsito.zapp.keys.Keys;
import com.justinmutsito.zapp.util.AudioFile;
import com.justinmutsito.zapp.util.YoutubeSearch;
import com.justinmutsito.zapp.util.YoutubeVideo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MediaActivity extends AppCompatActivity implements AudioBrowserFragment.AudioCallback {

    @BindView(R.id.videosIcon)
    ImageView mVideosIcon;
    @BindView(R.id.tvIcon)
    ImageView mTvIcon;
    @BindView(R.id.musicIcon)
    ImageView mMusicIcon;
    @BindView(R.id.container)
    ConstraintLayout mContainer;
    @BindView(R.id.exoplayer)
    SimpleExoPlayerView mExoplayer;


    private ArrayList<YoutubeVideo> mYoutubeVideosList;
    private ArrayList<AudioFile> mAudioFiles;
    private VideoBrowserFragment mVideoBrowserFragment;
    private AudioBrowserFragment mAudioBrowserFragment;
    private SimpleExoPlayer mPlayer;
    private boolean mPlayWhenReady;
    private int mCurrentWindow;
    private long mPlaybackPosition;
    private int mAudioFilePos;
    private com.google.android.exoplayer2.Player.EventListener mPlayerListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);
        init();
        onVideosIconClicked();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            initializePlayer();
            hideUI();
        }
    }

    @OnClick(R.id.videosIcon)
    public void onVideosIconClicked() {
        resetIcons();
        mVideosIcon.setImageResource(R.drawable.ic_library_video_red);

        if (mYoutubeVideosList.isEmpty()) {
            VideoSearchTask vd = new VideoSearchTask();
            vd.execute();
        } else {

            mVideoBrowserFragment.setVideos(mYoutubeVideosList);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mVideoBrowserFragment).commit();
        }
        mExoplayer.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("StaticFieldLeak")
    private class VideoSearchTask extends AsyncTask<String, Void, ArrayList<YoutubeVideo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //LOADING ANIMATION
        }

        @Override
        protected ArrayList<YoutubeVideo> doInBackground(String... strings) {
            YoutubeSearch youtubeSearch = new YoutubeSearch(MediaActivity.this);
            ArrayList<YoutubeVideo> results = youtubeSearch.search();
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<YoutubeVideo> videos) {
            if (!videos.isEmpty()) {
                mYoutubeVideosList = videos;
                mVideoBrowserFragment.setVideos(mYoutubeVideosList);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mVideoBrowserFragment).commit();

            } else {
                //Todo : Data retrieval error

            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class AudioSearchTask extends AsyncTask<String, Void, ArrayList<AudioFile>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Loading Animation
        }

        @Override
        protected ArrayList<AudioFile> doInBackground(String... strings) {
            ArrayList<AudioFile> results = getAudioFiles();
            String requestUrl = "https://api.fanburst.com/users/" + Keys.FANBURST_USER_ID + "/tracks";
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder httpBuilder = HttpUrl.parse(requestUrl).newBuilder();
            httpBuilder.addQueryParameter("client_id", Keys.FANBURST_API_KEY);
            httpBuilder.addQueryParameter("client_secret", Keys.FANBURST_SECRET);
            httpBuilder.addQueryParameter("redirect_uri", Keys.FANBURST_CALLBACK);
            httpBuilder.addQueryParameter("site", Keys.FANBURST_SITE);
            httpBuilder.addQueryParameter("access_token", Keys.FANBURST_TOKEN);


            Request request = new Request.Builder().url(httpBuilder.build()).build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                String data = response.body().string();
                JSONArray dataArray = new JSONArray(data);


                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject audioObject = dataArray.getJSONObject(i);

                    String title = audioObject.getString("title");
                    double time = (audioObject.getInt("duration") / 60.00);
                    String duration = String.format("%.2f", time);
                    String thumbnailUrl = audioObject.getString("image_url");
                    String streamUrl = audioObject.getString("stream_url");


                    AudioFile audioFile = new AudioFile();
                    audioFile.setTitle(title);
                    audioFile.setTime(duration + "");
                    audioFile.setThumbnailUrl(thumbnailUrl);

                    HttpUrl.Builder streamUrlBuilder = HttpUrl.parse(streamUrl).newBuilder();
                    streamUrlBuilder.addQueryParameter("client_id", Keys.FANBURST_API_KEY);
                    streamUrlBuilder.addQueryParameter("client_secret", Keys.FANBURST_SECRET);
                    streamUrlBuilder.addQueryParameter("redirect_uri", Keys.FANBURST_CALLBACK);
                    streamUrlBuilder.addQueryParameter("site", Keys.FANBURST_SITE);
                    streamUrlBuilder.addQueryParameter("access_token", Keys.FANBURST_TOKEN);

                    String url = streamUrlBuilder.toString();

                    audioFile.setStreamUrl(url);

                    results.add(audioFile);

                }


            } catch (Exception e) {
                e.printStackTrace();
                //Do nothing
            }


            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<AudioFile> audioFiles) {
            if (!audioFiles.isEmpty()) {
                mAudioFiles = audioFiles;
                mAudioBrowserFragment.setAllAudioFiles(mAudioFiles);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mAudioBrowserFragment).commit();

            } else {
                //Todo : Data retrieval error

            }


        }
    }


    public ArrayList<AudioFile> getAudioFiles() {
        return mAudioFiles;
    }


    @OnClick(R.id.tvIcon)
    public void onTvIconClicked() {
        startActivity(new Intent(MediaActivity.this, TvStreamActivity.class));


    }

    @OnClick(R.id.musicIcon)
    public void onMusicIconClicked() {
        resetIcons();
        mMusicIcon.setImageResource(R.drawable.ic_library_music_red);

        if (mAudioFiles.isEmpty()) {
            AudioSearchTask audioSearchTask = new AudioSearchTask();
            audioSearchTask.execute();
        } else {

            mAudioBrowserFragment.setAllAudioFiles(mAudioFiles);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mAudioBrowserFragment).commit();
        }
    }

    private void resetIcons() {
        mVideosIcon.setImageResource(R.drawable.ic_library_video_white);
        mTvIcon.setImageResource(R.drawable.ic_live_tv_white);
        mMusicIcon.setImageResource(R.drawable.ic_library_music_white);
    }

    private void init() {
        hideUI();
        mYoutubeVideosList = new ArrayList<>();
        mAudioFiles = new ArrayList<>();
        mVideoBrowserFragment = new VideoBrowserFragment();
        mAudioBrowserFragment = new AudioBrowserFragment();
        mPlayerListener = new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    mAudioFilePos++;
                    playAudio(mAudioFilePos);
                }

                if(playbackState == Player.STATE_BUFFERING){
                    //Todo : display loading icon
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        };
        initializePlayer();
    }


    private void hideUI() {
        mContainer.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void initializePlayer() {
        mPlayWhenReady = true;
        mCurrentWindow = 0;
        mPlaybackPosition = 0;
        mAudioFilePos = 0;

        mPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayer.setPlayWhenReady(mPlayWhenReady);
        mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        mPlayer.addListener(mPlayerListener);
        mExoplayer.setPlayer(mPlayer);

    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void playAudio(int pos) {
        mAudioFilePos = pos;
        MediaSource mediaSource = buildMediaSource(pos);
        mPlayer.prepare(mediaSource, true, false);
        mExoplayer.setVisibility(View.VISIBLE);


    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private MediaSource buildMediaSource(int pos) {
        DynamicConcatenatingMediaSource concatenatingMediaSource = new DynamicConcatenatingMediaSource();
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        for (int i = pos; i < mAudioFiles.size(); i++) {
            Uri uri = Uri.parse(mAudioFiles.get(i).getStreamUrl());
            MediaSource file = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
            concatenatingMediaSource.addMediaSource(file);
        }

        return concatenatingMediaSource;
    }


    @Override
    public void onPause() {
        super.onPause();

        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}
