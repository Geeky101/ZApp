package com.justinmutsito.zapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.adapters.EventsAdapter;
import com.justinmutsito.zapp.util.Event;
import com.justinmutsito.zapp.util.TimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class EventsTodayFragment extends Fragment {

    @BindView(R.id.dayLabel)
    TextView mDayLabel;
    @BindView(R.id.dateLabel)
    TextView mDateLabel;
    @BindView(R.id.eventsListView)
    ListView mEventsListView;
    Unbinder unbinder;

    private EventsTodayCallback mCallback;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsReference;
    private ChildEventListener mEventsListener;
    private ArrayList<Event> mAllEventsList;
    private ArrayList<Event> mTodayEventsList;
    private EventsAdapter mEventsAdapter;
    private int mDate,mMonth,mDayOfWeek;



    public EventsTodayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");
        mAllEventsList = new ArrayList<>();
        mTodayEventsList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        mDate = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_today, container, false);
        unbinder = ButterKnife.bind(this, view);

        mEventsAdapter = new EventsAdapter(getActivity(), mTodayEventsList);
        mEventsListView.setAdapter(mEventsAdapter);
        mDayLabel.setText(TimeFormatter.formatDay(mDayOfWeek));
        mDateLabel.setText(mDate+"");

        mEventsListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                mAllEventsList.add(event);

                if (event.getDay()==mDate && TimeFormatter.formatMonth(event.getMonth())==mMonth){
                    mTodayEventsList.add(event);
                    mEventsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mEventsReference.addChildEventListener(mEventsListener);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventsTodayCallback) {
            mCallback = (EventsTodayCallback) context;
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


    public interface EventsTodayCallback {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
