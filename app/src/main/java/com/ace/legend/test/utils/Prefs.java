package com.ace.legend.test.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rohan on 6/27/15.
 */
public class Prefs {

    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private static final String PREF_FILE_NAME = "TestApp";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;
    private Context context;

    public Prefs(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        this.prefsEditor = sharedPreferences.edit();
    }

    public void setUserLearnedDrawer(Boolean value) {
        prefsEditor.putBoolean(KEY_USER_LEARNED_DRAWER, value);
        prefsEditor.apply();
    }

    public boolean getUserLearnedDrawer(){
        return sharedPreferences.getBoolean(KEY_USER_LEARNED_DRAWER, false);
    }
}
