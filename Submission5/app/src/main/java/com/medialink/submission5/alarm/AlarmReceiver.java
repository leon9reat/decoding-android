package com.medialink.submission5.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.medialink.submission5.Const;
import com.medialink.submission5.model.movie.MovieRespon;
import com.medialink.submission5.model.movie.MovieResult;
import com.medialink.submission5.model.network.ApiInterface;
import com.medialink.submission5.model.network.RetrofitClient;
import com.medialink.submission5.notification.MovieNotif;
import com.medialink.submission5.notification.NotifItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    public static final String EXTRA_TYPE = "type";

    private String DATE_FORMAT = "yyyy-MM-dd";
    private String TIME_FORMAT = "HH:mm";

    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra(EXTRA_TYPE, 0);
        if (intent.getAction() != null) {
            Log.d(TAG, "onReceive: " + intent.getAction());
        }

        if (type == Const.REQUEST_DAILY_REMINDER) {
            MovieNotif notif = MovieNotif.getInstance(context);
            NotifItem notifItem = new NotifItem(0, "Daily Reminder", "Hi, We miss you...");
            notif.newNotif(notifItem);

            Log.d(TAG, "onReceive: tampilkan notification daily");
        } else if (type == Const.REQUEST_RELEASE_REMINDER) {
            MovieNotif notif = MovieNotif.getInstance(context);
            getNewMovie(notif);

            Log.d(TAG, "onReceive: tampilkan notification release");
        }

    }

    public void setRepeatingAlarm(Context context, int type) {

        //if (isDateInvalid(time, TIME_FORMAT)) return;


        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        String time = "00:00";
        if (type == Const.REQUEST_RELEASE_REMINDER) {
            time = Const.TIME_RELEASE_REMINDER;
        } else {
            time = Const.TIME_DAILY_REMINDER;
        }
        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                type,
                intent,
                0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
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

            Log.d(TAG, "setRepeatingAlarm: Set " + type + " = " + time);
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

    public void cancelAlarm(Context context, int type) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, type, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Log.d(TAG, "cancelAlarm: Alarm Dibatalkan " + type);
    }

    private void getNewMovie(final MovieNotif notif) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiInterface api = retrofit.create(ApiInterface.class);

        // setting bahasa
        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String drTgl = format.format(new Date());
        String spTgl = format.format(new Date());

        Call<MovieRespon> call = api.getMovieRelease(1, lang, drTgl, spTgl);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(Call<MovieRespon> call, Response<MovieRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResults() != null) {
                        ArrayList<MovieResult> list = new ArrayList<>(response.body().getResults());
                        for (int i = 0; i < list.size(); i++) {
                            NotifItem item = new NotifItem(list.get(i).getId(),
                                    list.get(i).getTitle(),
                                    list.get(i).getOverview());
                            notif.newNotif(item);
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse: Error " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieRespon> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }
}
