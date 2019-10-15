package com.medialink.submission5.alarm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.medialink.submission5.Const;

import java.util.Calendar;

public class AlarmService extends IntentService {

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String[] timeArray = Const.ALARM_RELEASE_TIME.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intent.putExtra(EXTRA_TYPE, Const.ALARM_REPEATING);
        intent.putExtra(EXTRA_MESSAGE, "Pesan Disini");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                Const.REQUEST_REPEAT,
                intent,
                0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }
}
