package com.justinmutsito.zapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.justinmutsito.zapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class EventsMonthFragment extends Fragment {


    @BindView(R.id.monthLabel)
    TextView mMonthLabel;
    @BindView(R.id.calendar)
    CompactCalendarView mCalendar;
    @BindView(android.R.id.list)
    ListView mList;
    Unbinder unbinder;

    private ArrayList<Event> mMonthEvents;
    private int mMonthIndex, mYear;

    public EventsMonthFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_month, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }



    private ArrayList<Event> getMonthEvents(int monthIndex) {
        return null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
