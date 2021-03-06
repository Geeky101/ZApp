package com.justinmutsito.zapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.util.AudioFile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by justin on 11/14/17.
 */

public class AudioFileListAdapter extends RecyclerView.Adapter<AudioFileListAdapter.SongViewHolder> {

    private ArrayList<AudioFile> mSongsList;
    private Context mContext;

    public AudioFileListAdapter(ArrayList<AudioFile> songsList) {
        mSongsList = songsList;
    }



    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_audio_file, parent, false);
        SongViewHolder songViewHolder = new SongViewHolder(view);
        return songViewHolder;
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.bindSong(mSongsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongsList.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        public TextView  titleLabel, durationLabel;
        public CircleImageView albumArt;


        public SongViewHolder(View itemView) {
            super(itemView);

            titleLabel = itemView.findViewById(R.id.titleLabel);
            durationLabel = itemView.findViewById(R.id.durationLabel);
            albumArt = itemView.findViewById(R.id.albumArt);

        }


        public void bindSong(AudioFile audioFile) {
            if (audioFile.getTitle().length() > 25) {
                String title = audioFile.getTitle().substring(0, 20) + "...";
                titleLabel.setText(title);
            } else {
                titleLabel.setText(audioFile.getTitle());
            }


            durationLabel.setText(audioFile.getTime());


            Picasso.get()
                    .load(audioFile.getThumbnailUrl())
                    .resize(800, 600)
                    .placeholder(R.drawable.bg_thumbnail)
                    .error(R.drawable.bg_thumbnail)
                    .into(albumArt);

        }


    }


}
