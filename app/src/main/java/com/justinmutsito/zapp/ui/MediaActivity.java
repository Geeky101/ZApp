package com.justinmutsito.zapp.ui;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.justinmutsito.zapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaActivity extends AppCompatActivity {

    @BindView(R.id.videosIcon)
    ImageView mVideosIcon;
    @BindView(R.id.tvIcon)
    ImageView mTvIcon;
    @BindView(R.id.musicIcon)
    ImageView mMusicIcon;
    @BindView(R.id.imagesIcon)
    ImageView mImagesIcon;
    @BindView(R.id.container)
    ConstraintLayout mContainer;

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



        onVideosIconClicked();
    }

    @OnClick(R.id.videosIcon)
    public void onVideosIconClicked() {
        resetIcons();
        mVideosIcon.setImageResource(R.drawable.ic_library_video_red);





    }

    @OnClick(R.id.tvIcon)
    public void onTvIconClicked() {
        resetIcons();
        mTvIcon.setImageResource(R.drawable.ic_live_tv_red);
    }

    @OnClick(R.id.musicIcon)
    public void onMusicIconClicked() {
        resetIcons();
        mMusicIcon.setImageResource(R.drawable.ic_library_music_red);
    }

    @OnClick(R.id.imagesIcon)
    public void onImagesIconClicked() {
        resetIcons();
        mImagesIcon.setImageResource(R.drawable.ic_photo_library_red);

    }


    private void resetIcons() {
        mVideosIcon.setImageResource(R.drawable.ic_library_video_white);
        mTvIcon.setImageResource(R.drawable.ic_live_tv_white);
        mMusicIcon.setImageResource(R.drawable.ic_library_music_white);
        mImagesIcon.setImageResource(R.drawable.ic_photo_library_white);
    }

}
