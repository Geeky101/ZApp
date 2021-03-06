package com.justinmutsito.zapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.adapters.VideoListAdapter;
import com.justinmutsito.zapp.ui.VideoStreamerActivity;
import com.justinmutsito.zapp.util.YoutubeVideo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoBrowserFragment extends Fragment implements VideoListAdapter.VideoCallback {

    @BindView(R.id.videosListView)
    RecyclerView mVideosListView;
    Unbinder unbinder;
    private ArrayList<YoutubeVideo> mAllVideos;
    private VideoListAdapter mVideoListAdapter;

    public VideoBrowserFragment() {
        // Required empty public constructor
    }

    public void setVideos(ArrayList<YoutubeVideo> videos) {
        mAllVideos = videos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_browser, container, false);
        unbinder = ButterKnife.bind(this, view);
        mVideoListAdapter = new VideoListAdapter(mAllVideos);
        mVideoListAdapter.setVideoCallback(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mVideosListView.setLayoutManager(layoutManager);
        mVideosListView.setHasFixedSize(true);
        mVideosListView.setAdapter(mVideoListAdapter);

        Toast.makeText(getActivity(), "have " + mAllVideos.size(), Toast.LENGTH_SHORT).show();

        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void playVideo(int pos) {
        Intent intent = new Intent(getActivity(), VideoStreamerActivity.class);
        Bundle bundle = new Bundle();
        String id = mAllVideos.get(pos).getId();
        bundle.putString("id", id);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




}
