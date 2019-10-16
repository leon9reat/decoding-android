package com.medialink.submission5.preference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.PreferenceFragmentCompat;

import com.medialink.submission5.Const;
import com.medialink.submission5.R;
import com.medialink.submission5.alarm.AlarmReceiver;

public class PreferenceFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "PreferenceFragment";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String key_release = getString(R.string.pref_key_release_reminder);
        String key_daily = getString(R.string.pref_key_daily_reminder);
        AlarmReceiver alarmReceiver = new AlarmReceiver();

        if (sharedPreferences.getBoolean(key, false)) {
            if (key_release.equals(key)) {
                alarmReceiver.setRepeatingAlarm(getContext(), Const.REQUEST_RELEASE_REMINDER);
            }
            if (key_daily.equals(key)) {
                alarmReceiver.setRepeatingAlarm(getContext(), Const.REQUEST_DAILY_REMINDER);
            }

            Log.d(TAG, "onSharedPreferenceChanged: hidupin alarm");
        } else {
            if (key_release.equals(key)) {
                alarmReceiver.cancelAlarm(getContext(), Const.REQUEST_RELEASE_REMINDER);
            }
            if (key_daily.equals(key)) {
                alarmReceiver.cancelAlarm(getContext(), Const.REQUEST_DAILY_REMINDER);
            }

            Log.d(TAG, "onSharedPreferenceChanged: matiin alarm");
        }
    }
}
