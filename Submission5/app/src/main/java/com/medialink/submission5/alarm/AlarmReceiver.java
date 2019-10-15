package com.medialink.submission5.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.medialink.submission5.Const;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    private String DATE_FORMAT = "yyyy-MM-dd";
    private String TIME_FORMAT = "HH:mm";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                // jika baru direstart, akan hidupin service menghasilkan intent alarm
                Intent serviceIntent = new Intent(context, AlarmService.class);
                context.startService(serviceIntent);

                Log.d(TAG, "onReceive: Alarm diset ulang setelah restart");

                return;
            }
        }

        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        String title = type.equalsIgnoreCase(Const.ALARM_ONE_TIME) ? Const.ALARM_ONE_TIME : Const.ALARM_REPEATING;
        int notifId = type.equalsIgnoreCase(Const.ALARM_ONE_TIME) ? Const.REQUEST_ONE_TIME : Const.REQUEST_REPEAT;

        Log.d(TAG, "onReceive: Alarm " + title + ", " + message);

    }

    public void setRepeatingAlarm(Context context, String type, String message) {

        //if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String[] timeArray = Const.ALARM_RELEASE_TIME.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
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


            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
            }
            */


            Log.d(TAG, "setRepeatingAlarm: Set " + Const.ALARM_RELEASE_TIME);
        }

    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public void cancelAlarm(Context context, String type) {

        // TODO : nanti alarm harus di ubah menjadi 2 jenis, ambil data dari preference
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(Const.ALARM_ONE_TIME) ? Const.REQUEST_ONE_TIME : Const.REQUEST_REPEAT;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Log.d(TAG, "cancelAlarm: Alarm Dibatalkan");
    }
}
