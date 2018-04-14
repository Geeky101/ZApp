package com.justinmutsito.zapp.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.keys.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoStreamerActivity extends YouTubeBaseActivity {


    @BindView(R.id.player)
    YouTubePlayerView mPlayer;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.playPauseIcon)
    ImageView mPlayPauseIcon;
    @BindView(R.id.currentTimeLabel)
    TextView mCurrentTimeLabel;
    @BindView(R.id.durationLabel)
    TextView mDurationLabel;


    private Handler mSeekBarHandler;
    private Runnable mSeekBarRunnable;
    private boolean mVideoPaused;
    private YouTubePlayer mYouTubePlayer;
    private boolean mSeekBarUISet;
    private int mPausePosition;
    private String mVideoId;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_video_streamer);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mVideoId = bundle.getString("id");
        mSeekBarUISet = false;
        mVideoPaused = false;
        mPausePosition = 0;

        mPlayer.initialize(Keys.YOUTUBE_DATA_API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(mVideoId);
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                        youTubePlayer.setShowFullscreenButton(false);
                        mYouTubePlayer = youTubePlayer;
                        setSeekBar();

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mYouTubePlayer.seekToMillis(seekBar.getProgress());
            }
        });


        hideSystemUi();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSeekBarHandler.removeCallbacks(mSeekBarRunnable);
    }

    @OnClick(R.id.playPauseIcon)
    public void onPlayPauseIconClicked() {
        if (mVideoPaused) {
            mYouTubePlayer.play();
            mPlayPauseIcon.setImageResource(R.drawable.ic_pause_circle_red);
            mVideoPaused = false;
            setSeekBar();

        } else {
            mYouTubePlayer.pause();
            mPlayPauseIcon.setImageResource(R.drawable.ic_play_circle_red);
            mVideoPaused = true;
            mSeekBarHandler.removeCallbacks(mSeekBarRunnable);

        }
    }

    private void hideSystemUi() {
        mPlayer.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void setSeekBar() {
        mSeekBarHandler = new Handler();
        mSeekBarRunnable = new Runnable() {
            @Override
            public void run() {
                if (!mVideoPaused) {
                    mSeekBar.setProgress(mYouTubePlayer.getCurrentTimeMillis());
                    mCurrentTimeLabel.setText(getTime(mYouTubePlayer.getCurrentTimeMillis()));

                }

                if (mYouTubePlayer.getCurrentTimeMillis() > 0 && !mSeekBarUISet) {
                    mSeekBar.setMax(mYouTubePlayer.getDurationMillis());
                    mDurationLabel.setText(getTime(mYouTubePlayer.getDurationMillis()));
                    mSeekBarUISet = true;
                }

                if (mYouTubePlayer.getCurrentTimeMillis() > 0) {
                    if (mYouTubePlayer.getCurrentTimeMillis() == mYouTubePlayer.getDurationMillis()) {
                        onBackPressed();
                    }
                }
                mSeekBarHandler.postDelayed(this, 1000);
            }
        };

        mSeekBarHandler.post(mSeekBarRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPausePosition = mYouTubePlayer.getCurrentTimeMillis();


    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
        if (mPausePosition>0){
            mYouTubePlayer.loadVideo(mVideoId,mPausePosition);
        }

    }

    private String getTime(int time) {

        int inSecs = time / 1000;
        int hours = inSecs / 3600;
        int min = (inSecs - (hours * 3600)) / 60;
        int sec = inSecs - (hours * 3600) - (min * 60);
        String theTime;

        if (hours == 0) {
            if (min < 10) {
                if (sec < 10) {
                    theTime = "0" + min + ":0" + sec;
                } else {
                    theTime = "0" + min + ":" + sec;
                }
            } else {
                if (sec < 10) {
                    theTime = min + ":0" + sec;
                } else {
                    theTime = min + ":" + sec;
                }

            }
        } else {
            if (min < 10) {
                if (sec < 10) {
                    theTime = hours + ":0" + min + ":0" + sec;
                } else {
                    theTime = hours + ":0" + min + ":" + sec;
                }
            } else {
                if (sec < 10) {
                    theTime = hours + ":" + min + ":0" + sec;
                } else {
                    theTime = hours + ":" + min + ":" + sec;
                }

            }
        }

        return theTime;
    }


}
