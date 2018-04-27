package com.justinmutsito.zapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.justinmutsito.zapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bgGradient)
    ImageView mBgGradient;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.logoImage)
    ImageView mLogoImage;
    @BindView(R.id.eventsLayout)
    LinearLayout mEventsLayout;
    @BindView(R.id.mediaLayout)
    LinearLayout mMediaLayout;
    @BindView(R.id.groupsLayout)
    LinearLayout mGroupsLayout;
    @BindView(R.id.paymentsLayout)
    LinearLayout mPaymentsLayout;
    @BindView(R.id.infoLayout)
    LinearLayout mInfoLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (item.getItemId() == R.id.about){
            startActivity(new Intent(this, AboutActivity.class));
        }
        else {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        return true;
    }

    @OnClick(R.id.eventsLayout)
    public void onEventsLayoutClicked() {
        startActivity(new Intent(MainActivity.this,EventsActivity.class));
    }

    @OnClick(R.id.mediaLayout)
    public void onMediaLayoutClicked() {
        startActivity(new Intent(MainActivity.this,MediaActivity.class));
    }

    @OnClick(R.id.groupsLayout)
    public void onGroupsLayoutClicked() {
        startActivity(new Intent(MainActivity.this,GroupsActivity.class));
    }

    @OnClick(R.id.paymentsLayout)
    public void onPaymentsLayoutClicked() {
        startActivity(new Intent(MainActivity.this,PaymentsActivity.class));
    }

    @OnClick(R.id.infoLayout)
    public void onInfoLayoutClicked() {
        startActivity(new Intent(MainActivity.this,InfoActivity.class));
    }
}
