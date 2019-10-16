package com.medialink.submission5.preference;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.medialink.submission5.R;

public class PreferenceHelper {

    private static PreferenceHelper mPrefHelper;
    private static SharedPreferences mPref;
    private static Context mContext;

    private boolean isRelesedReminder;
    private boolean isDailyReminder;

    public PreferenceHelper() {
    }

    public static PreferenceHelper getInstance(Context context) {
        if (mPrefHelper == null) {
            mPrefHelper = new PreferenceHelper();
            mContext = context;
            mPref = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return mPrefHelper;
    }

    public boolean isRelesedReminder() {
        String key = mContext.getString(R.string.pref_key_release_reminder);
        isRelesedReminder = mPref.getBoolean(key, true);
        return isRelesedReminder;
    }

    public void setRelesedReminder(boolean relesedReminder) {
        isRelesedReminder = relesedReminder;
    }

    public boolean isDailyReminder() {
        String key = mContext.getString(R.string.pref_key_daily_reminder);
        isDailyReminder = mPref.getBoolean(key, true);
        return isDailyReminder;
    }

    public void setDailyReminder(boolean dailyReminder) {
        isDailyReminder = dailyReminder;
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }
}
