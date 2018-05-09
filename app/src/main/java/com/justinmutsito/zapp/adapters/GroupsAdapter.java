package com.justinmutsito.zapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.util.Group;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupsAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Group> mGroupsList;

    public GroupsAdapter(Context context, ArrayList<Group> groupsList) {
        mContext = context;
        mGroupsList = groupsList;
    }

    @Override
    public int getCount() {
        return mGroupsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroupsList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_group, null);
            holder.groupAvatar = convertView.findViewById(R.id.groupAvatar);
            holder.groupLabel = convertView.findViewById(R.id.groupLabel);
            holder.unreadLabel = convertView.findViewById(R.id.unreadLabel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Group group = mGroupsList.get(position);

        holder.groupLabel.setText(group.getName());
        holder.unreadLabel.setText(group.getUnread());
        Picasso.get()
                .load(group.getAvatarUrl())
                .resize(800, 600)
                .placeholder(R.drawable.bg_thumbnail)
                .error(R.drawable.bg_thumbnail)
                .into(holder.groupAvatar);

        return convertView;
    }

    public class ViewHolder {
        public CircleImageView groupAvatar;
        public TextView groupLabel;
        public TextView unreadLabel;
    }

}
