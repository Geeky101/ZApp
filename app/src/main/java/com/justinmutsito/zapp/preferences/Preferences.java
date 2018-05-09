package com.justinmutsito.zapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by justin on 11/6/17.
 */

public class Preferences {
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
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

    public void setPassword(String password) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PASSWORD, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.apply();
    }

    public String getPassword() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PASSWORD, 0);
        return sharedPreferences.getString("password", "password");
    }

}
