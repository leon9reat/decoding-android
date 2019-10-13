package com.medialink.submission5.preference;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.medialink.submission5.R;

public class PreferenceHelper {

    private final SharedPreferences mPref;
    private Context context;

    private boolean isRelesedReminder;
    private boolean isDailyReminder;

    public PreferenceHelper(Context context) {
        this.context = context;
        this.mPref = PreferenceManager.getDefaultSharedPreferences(context);
        //= context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isRelesedReminder() {
        String key = context.getString(R.string.pref_key_release_reminder);
        isRelesedReminder = mPref.getBoolean(key, true);
        return isRelesedReminder;
    }

    public void setRelesedReminder(boolean relesedReminder) {
        isRelesedReminder = relesedReminder;
    }

    public boolean isDailyReminder() {
        String key = context.getString(R.string.pref_key_daily_reminder);
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
