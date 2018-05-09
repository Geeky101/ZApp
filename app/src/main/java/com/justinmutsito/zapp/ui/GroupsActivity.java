package com.justinmutsito.zapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.adapters.GroupsAdapter;
import com.justinmutsito.zapp.util.Group;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class GroupsActivity extends AppCompatActivity {


    @BindView(R.id.groupsLabel)
    TextView mGroupsLabel;
    @BindView(R.id.groupsListVIew)
    ListView mGroupsListVIew;

    private ArrayList<Group> mGroups;
    private GroupsAdapter mGroupsAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mGroupReference;
    private ChildEventListener mGroupListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        ButterKnife.bind(this);
        init();
    }


    @OnItemClick(R.id.groupsListVIew)
    public void onItemlicked(int position) {
        Toast.makeText(this, "this is " + position, Toast.LENGTH_SHORT).show();

    }

    public void init() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mGroupReference = mFirebaseDatabase.getReference().child("groups");
        mGroups = new ArrayList<>();
        mGroupsAdapter = new GroupsAdapter(GroupsActivity.this, mGroups);
        mGroupsListVIew.setAdapter(mGroupsAdapter);
        mGroupListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Group group = dataSnapshot.getValue(Group.class);
                mGroups.add(group);
                Toast.makeText(GroupsActivity.this, "size is " + mGroups.size(), Toast.LENGTH_SHORT).show();
                mGroupsAdapter.notifyDataSetChanged();
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
        mGroupReference.addChildEventListener(mGroupListener);
    }
}
