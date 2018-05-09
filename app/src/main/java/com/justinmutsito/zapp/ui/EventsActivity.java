package com.justinmutsito.zapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.fragments.EventsMonthFragment;
import com.justinmutsito.zapp.fragments.EventsTodayFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventsActivity extends AppCompatActivity implements EventsTodayFragment.EventsTodayCallback {


    @BindView(R.id.backIcon)
    ImageView mBackIcon;
    @BindView(R.id.moreIcon)
    ImageView mSettingsIcon;
    @BindView(R.id.fragmentContainer)
    RelativeLayout mFragmentContainer;
    @BindView(R.id.upcomingLabel)
    TextView mUpcomingLabel;

    private EventsTodayFragment mEventsTodayFragment;
    private EventsMonthFragment mEventsMonthFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mEventsTodayFragment = new EventsTodayFragment();
        mEventsMonthFragment = new EventsMonthFragment();
        loadTodayFragment();
    }


    @OnClick(R.id.backIcon)
    public void onBackIconClicked() {
        onBackPressed();
    }

    @OnClick(R.id.moreIcon)
    public void onSettingsIconClicked() {
        showOptions().show();
    }

    private Dialog showOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"Today", " This Month"};
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                loadTodayFragment();
            } else {
                loadMonthFragment();
            }
            dialog.dismiss();

        });

        return builder.create();
    }

    private void loadTodayFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mEventsTodayFragment).commit();
    }

    private void loadMonthFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mEventsMonthFragment).commit();
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
