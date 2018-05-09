package com.justinmutsito.zapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
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
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class EventsMonthFragment extends Fragment {


    @BindView(R.id.monthLabel)
    TextView mMonthLabel;
    @BindView(R.id.emptyLabel)
    TextView mEmptyLabel;
    @BindView(R.id.calendar)
    CompactCalendarView mCalendarView;
    @BindView(android.R.id.list)
    ListView mEventsListView;
    Unbinder unbinder;

    private ArrayList<Event> mMonthEvents;
    private ArrayList<Event> mAllEventsList;
    private int mMonthIndex, mCurrentMonth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsReference;
    private ChildEventListener mEventsListener;
    private Calendar mCalendar;


    public EventsMonthFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");
        mMonthEvents = new ArrayList<>();
        mAllEventsList = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mCurrentMonth = mCalendar.get(Calendar.MONTH);
        mMonthIndex = mCurrentMonth;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_month, container, false);
        unbinder = ButterKnife.bind(this, view);

        mEventsListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(com.justinmutsito.zapp.util.Event.class);
                mAllEventsList.add(event);

                int monthIndex = TimeFormatter.formatMonth(event.getMonth());
                if (monthIndex == mCurrentMonth) {
                    mMonthEvents.add(event);
                    updateCalendarEvents(mCurrentMonth, mMonthEvents);
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


    private ArrayList<Event> getMonthEvents(int monthIndex) {
        ArrayList<Event> monthEvents = new ArrayList<>();
        for (Event event : mAllEventsList) {
            if (event.getMonthIndex() == monthIndex) {
                monthEvents.add(event);
            }
        }

        return monthEvents;
    }

    private ArrayList<Event> getDayEvents(int day) {
        ArrayList<Event> dayEvents = new ArrayList<>();

        for (Event event : mMonthEvents) {
            if (event.getDay() == day) {
                dayEvents.add(event);
            }
        }

        return dayEvents;
    }

    private void setEventsList(ArrayList<Event> events) {
        EventsAdapter adapter = new EventsAdapter(getActivity(), events);
        mEventsListView.setAdapter(adapter);

        mEventsListView.setOnItemClickListener((parent, view, position, id) -> {
            showEventDetails(events.get(position)).show();
        });

    }

    private Dialog showEventDetails(Event event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_event_dialog, null);
        TextView dayLabel = view.findViewById(R.id.dayLabel);
        TextView detailsLabel = view.findViewById(R.id.detailsLabel);
        TextView locationLabel = view.findViewById(R.id.locationLabel);
        TextView startTimeLabel = view.findViewById(R.id.startTimeLabel);
        TextView finishTimeLabel = view.findViewById(R.id.finishTimeLabel);

        dayLabel.setText(event.getDate());
        detailsLabel.setText(event.getDetails());
        locationLabel.setText(event.getLocation());
        startTimeLabel.setText(event.getStartTime());
        finishTimeLabel.setText(event.getFinishTime());

        builder.setView(view);


        return builder.create();

    }


    private void updateCalendarEvents(int month, ArrayList<Event> events) {

        int day = mCalendar.get(Calendar.DATE);
        int year = mCalendar.get(Calendar.YEAR);
        mMonthLabel.setText(TimeFormatter.formatMonth(month));

        mCalendarView.removeAllEvents();
        final Calendar calendar = Calendar.getInstance();

        if (events.isEmpty()) {
            mEmptyLabel.setVisibility(View.VISIBLE);
            mEventsListView.setVisibility(View.INVISIBLE);
        } else {
            for (Event event : events) {
                mEmptyLabel.setVisibility(View.INVISIBLE);
                mEventsListView.setVisibility(View.VISIBLE);

                calendar.set(year, event.getMonthIndex(), event.getDay());
                com.github.sundeepk.compactcalendarview.domain.Event calendarEvent;
                if (event.getDay() < day) {
                    calendarEvent = new com.github.sundeepk.compactcalendarview.domain.Event(R.color.colorAccent, calendar.getTimeInMillis());
                } else {
                    calendarEvent = new com.github.sundeepk.compactcalendarview.domain.Event(R.color.colorPrimaryBlueDark, calendar.getTimeInMillis());
                }
                mCalendarView.addEvent(calendarEvent, true);

            }

            setEventsList(events);
        }


        mCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateClicked);
                setEventsList(getDayEvents(calendar.get(Calendar.DATE)));


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(firstDayOfNewMonth);

                mMonthIndex = calendar.get(Calendar.MONTH);
                mMonthLabel.setText(TimeFormatter.formatMonth(mMonthIndex));

                mMonthEvents = getMonthEvents(mMonthIndex );

                Calendar today = Calendar.getInstance();
                today.setTime(new Date());

                if (mMonthEvents.isEmpty()) {
                    mEmptyLabel.setVisibility(View.VISIBLE);
                    mEventsListView.setVisibility(View.INVISIBLE);
                } else {
                    for (Event event : mMonthEvents) {
                        mEmptyLabel.setVisibility(View.INVISIBLE);
                        mEventsListView.setVisibility(View.VISIBLE);
                        calendar.set(year, event.getMonthIndex(), event.getDay());
                        com.github.sundeepk.compactcalendarview.domain.Event calendarEvent;
                        if (event.getDay() < day) {
                            calendarEvent = new com.github.sundeepk.compactcalendarview.domain.Event(R.color.colorAccent, calendar.getTimeInMillis());
                        } else {
                            calendarEvent = new com.github.sundeepk.compactcalendarview.domain.Event(R.color.colorPrimaryBlueDark, calendar.getTimeInMillis());
                        }
                        mCalendarView.addEvent(calendarEvent, true);

                    }

                    setEventsList(mMonthEvents);
                }


            }
        });


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
