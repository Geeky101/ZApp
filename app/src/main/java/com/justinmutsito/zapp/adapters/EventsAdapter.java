package com.justinmutsito.zapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.util.Event;
import com.justinmutsito.zapp.util.TimeFormatter;

import java.util.ArrayList;

public class EventsAdapter extends BaseAdapter {

    private ArrayList<Event> mEventsList;
    private Context mContext;

    public EventsAdapter(Context context, ArrayList<Event> eventsList) {
        mContext = context;
        mEventsList = eventsList;
    }

    @Override
    public int getCount() {
        return mEventsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEventsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_event, null);
            holder.completeIcon = convertView.findViewById(R.id.completeIcon);
            holder.eventDetails = convertView.findViewById(R.id.detailsLabel);
            holder.eventTime = convertView.findViewById(R.id.timeLabel);
            holder.eventLocation = convertView.findViewById(R.id.locationLabel);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Event event = mEventsList.get(position);

        holder.eventDetails.setText(event.getDetails());
        holder.eventLocation.setText(event.getLocation());
        String time =  TimeFormatter.formatTime(event.getStartHour(), event.getStartMinute()) + " to " + TimeFormatter.formatTime(event.getEndHour(), event.getEndMinute());
        holder.eventTime.setText(time);
        return convertView;
    }

    public class ViewHolder {
        public ImageView completeIcon;
        public TextView eventDetails, eventTime, eventLocation;
    }
}
