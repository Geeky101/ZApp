package com.justinmutsito.zapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.util.YoutubeVideo;

import java.util.ArrayList;

public class VideoBrowserFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<YoutubeVideo> mAllVideos;

    public VideoBrowserFragment() {
        // Required empty public constructor
    }

    public void setVideos(ArrayList<YoutubeVideo> videos){
        mAllVideos = videos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAllVideos = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_browser, container, false);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
