package com.justinmutsito.zapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.adapters.AudioFileListAdapter;
import com.justinmutsito.zapp.util.AudioFile;
import com.justinmutsito.zapp.util.ItemClickSupport;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AudioBrowserFragment extends Fragment {


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
        final View view = inflater.inflate(R.layout.fragment_music_browser, container, false);
        unbinder = ButterKnife.bind(this, view);

        mAudioFileListAdapter = new AudioFileListAdapter(mAllAudioFiles);
        mAudioListView.setAdapter(mAudioFileListAdapter);


        ItemClickSupport.addTo(mAudioListView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                mCallback.playAudio(position);
            }
        });
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.listview_divider));
        mAudioListView.addItemDecoration(itemDecorator);
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


    public interface AudioCallback {
        void playAudio(int pos);
    }
}
