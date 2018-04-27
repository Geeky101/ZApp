package com.justinmutsito.zapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by justin on 11/6/17.
 */

public class Preferences {
    private static final String USERNAME = "USERNAME";
    private Context mContext;

    public Preferences(Context context) {
        mContext = context;
    }

    public void setUsername(String username) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USERNAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USERNAME, 0);
        return sharedPreferences.getString("username", "null");

    }



}
