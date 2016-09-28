package com.mahii.alltests.UserInteraction;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by MaHi on 28-Sep-16.
 */

public class UserInterActionApplication extends Application {

    private static SharedPreferences sPreference;

    private static final long MIN_SAVE_TIME = 5000;
    private static final String PREF_KEY_LAST_ACTIVE = "last_active";
    private static final String PREF_ID_TIME_TRACK = "time_track";

    public Context getInstance() {
        return this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sPreference = getSharedPreferences(PREF_ID_TIME_TRACK, MODE_PRIVATE);
    }

    public static void saveTimeStamp() {
        if (getElapsedTime() > MIN_SAVE_TIME) {
            sPreference.edit().putLong(PREF_KEY_LAST_ACTIVE, timeNow()).commit();
        }
    }

    public static long getElapsedTime() {
        return timeNow() - sPreference.getLong(PREF_KEY_LAST_ACTIVE, 0);
    }

    private static long timeNow() {
        return Calendar.getInstance().getTimeInMillis();
    }

}