package com.justinmutsito.zapp.fragments;

import android.content.Context;
import android.net.Uri;
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
import com.justinmutsito.zapp.util.YoutubeVideo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoBrowserFragment extends Fragment implements VideoListAdapter.VideoCallback {

    @BindView(R.id.videosListView)
    RecyclerView mVideosListView;
    Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void playVideo(int pos) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
