package com.justinmutsito.zapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.adapters.AudioFileListAdapter;
import com.justinmutsito.zapp.util.AudioFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AudioBrowserFragment extends Fragment implements AudioFileListAdapter.Callback {


    @BindView(R.id.audioListView)
    RecyclerView mAudioListView;
    Unbinder unbinder;

    private ArrayList<AudioFile> mAllAudioFiles;
    private AudioFileListAdapter mAudioFileListAdapter;
    private AudioCallback mCallback;


    public AudioBrowserFragment() {
        // Required empty public constructor
    }

    public void setAllAudioFiles(ArrayList<AudioFile> allAudioFiles) {
        mAllAudioFiles = allAudioFiles;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_browser, container, false);
        unbinder = ButterKnife.bind(this, view);

        mAudioFileListAdapter = new AudioFileListAdapter(mAllAudioFiles);
        mAudioFileListAdapter.setCallback(this);
        mAudioListView.setAdapter(mAudioFileListAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAudioListView.setLayoutManager(layoutManager);
        mAudioListView.setHasFixedSize(true);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AudioCallback) {
            mCallback = (AudioCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void songOptions(int pos) {
        mCallback.playAudio(pos);
    }

    public interface AudioCallback {
        void playAudio(int pos);
        }
}
