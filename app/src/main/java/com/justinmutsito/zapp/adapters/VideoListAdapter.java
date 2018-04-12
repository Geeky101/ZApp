package com.justinmutsito.zapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.util.YoutubeVideo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by justin on 11/14/17.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {

    private  Context mContext;
    private ArrayList<YoutubeVideo> mVideosList;
    private VideoCallback mVideoCallback;

    public VideoListAdapter(ArrayList<YoutubeVideo> videosList) {
        mVideosList = videosList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video, parent, false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(view);

        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.bindVideo(mVideosList.get(position));
    }

    @Override
    public int getItemCount() {
        return mVideosList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public TextView titleLabel;
        public ImageView thumbnail;
        public Button playButton;

        public VideoViewHolder(final View itemView) {
            super(itemView);

            playButton = itemView.findViewById(R.id.playBtn);
            titleLabel = itemView.findViewById(R.id.titleLabel);
            thumbnail = itemView.findViewById(R.id.thumbnail);


            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mVideoCallback!=null){
                        mVideoCallback.playVideo(getAdapterPosition());
                    }
                }
            });
        }

        public void bindVideo(YoutubeVideo video) {

            if (video.getTitle().length() > 35) {
                String videoTitle = video.getTitle().substring(0, 32) + "...";
                titleLabel.setText(videoTitle);
            } else {
                titleLabel.setText(video.getTitle());
            }

            Picasso.get()
                    .load(video.getThumbnailUrl())
                    .resize(800, 600)
                    .placeholder(R.drawable.bg_thumbnail)
                    .error(R.drawable.bg_thumbnail)
                    .into(thumbnail);
        }



    }

    public interface VideoCallback {
        void playVideo(int pos);
    }

    public  void setVideoCallback(VideoCallback callback){
        mVideoCallback = callback;
    }

}
