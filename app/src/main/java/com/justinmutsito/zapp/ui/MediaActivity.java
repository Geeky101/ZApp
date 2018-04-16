package com.justinmutsito.zapp.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.fragments.AudioBrowserFragment;
import com.justinmutsito.zapp.fragments.VideoBrowserFragment;
import com.justinmutsito.zapp.keys.Keys;
import com.justinmutsito.zapp.util.AudioFile;
import com.justinmutsito.zapp.util.YoutubeSearch;
import com.justinmutsito.zapp.util.YoutubeVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MediaActivity extends AppCompatActivity{

    @BindView(R.id.videosIcon)
    ImageView mVideosIcon;
    @BindView(R.id.tvIcon)
    ImageView mTvIcon;
    @BindView(R.id.musicIcon)
    ImageView mMusicIcon;
    @BindView(R.id.container)
    ConstraintLayout mContainer;


    private ArrayList<YoutubeVideo> mYoutubeVideosList;
    private ArrayList<AudioFile> mAudioFiles;
    private VideoBrowserFragment mVideoBrowserFragment;
    private AudioBrowserFragment mAudioBrowserFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);
        mContainer.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        mYoutubeVideosList = new ArrayList<>();
        mAudioFiles = new ArrayList<>();
        mVideoBrowserFragment = new VideoBrowserFragment();
        mAudioBrowserFragment = new AudioBrowserFragment();
        onVideosIconClicked();
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

    }


    @SuppressLint("StaticFieldLeak")
    private class VideoSearchTask extends AsyncTask<String, Void, ArrayList<YoutubeVideo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MediaActivity.this, "Started", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MediaActivity.this, "Done", Toast.LENGTH_SHORT).show();

            } else {
                //Todo : Data retrieval error
                Toast.makeText(MediaActivity.this, "We have an error", Toast.LENGTH_SHORT).show();

            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class AudioSearchTask extends AsyncTask<String, Void, ArrayList<AudioFile>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MediaActivity.this, "Started audio", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<AudioFile> doInBackground(String... strings) {
            ArrayList<AudioFile> results = getAudioFiles();
            String requestUrl = "https://api.fanburst.com/users/ykqeow/tracks";
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder httpBuider = HttpUrl.parse(requestUrl).newBuilder();
            httpBuider.addQueryParameter("client_id", Keys.FANBURST_API_KEY);
            httpBuider.addQueryParameter("client_secret", Keys.FANBURST_SECRET);
            httpBuider.addQueryParameter("redirect_uri", Keys.FANBURST_CALLBACK);
            httpBuider.addQueryParameter("site", Keys.FANBURST_SITE);
            httpBuider.addQueryParameter("access_token", Keys.FANBURST_TOKEN);


            Request request = new Request.Builder().url(httpBuider.build()).build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                String data = response.body().string();
                JSONArray dataArray = new JSONArray(data);


                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject audioObject = dataArray.getJSONObject(i);

                    String title = audioObject.getString("title");
                    double duration = (double) (audioObject.getInt("duration") / 60);
                    String thumbnailUrl = audioObject.getString("image_url");
                    String streamUrl = audioObject.getString("stream_url");


                    AudioFile audioFile = new AudioFile();
                    audioFile.setTitle(title);
                    audioFile.setTime(duration + "");
                    audioFile.setThumbnailUrl(thumbnailUrl);
                    audioFile.setStreamUrl(streamUrl);

                    results.add(audioFile);

                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<AudioFile> audioFiles) {
            if (!audioFiles.isEmpty()) {
                mAudioFiles = audioFiles;
                mAudioBrowserFragment.setAllAudioFiles(mAudioFiles);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mAudioBrowserFragment).commit();
                Toast.makeText(MediaActivity.this, "Done", Toast.LENGTH_SHORT).show();

            } else {
                //Todo : Data retrieval error
                Toast.makeText(MediaActivity.this, "We have an error", Toast.LENGTH_SHORT).show();

            }



        }
    }


    public ArrayList<AudioFile> getAudioFiles() {
        return mAudioFiles;
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

}
