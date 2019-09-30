package com.medialink.mydeepnavigation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
    implements AsyncCallback {

    @BindView(R.id.btn_open_detail)
    Button btnOpenDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DelayAsync delayAsync = new DelayAsync(this);
        delayAsync.execute();
    }

    @OnClick(R.id.btn_open_detail)
    public void onClick(View v) {
        if (v.getId() == R.id.btn_open_detail) {
            Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
            detailIntent.putExtra(DetailActivity.EXTRA_TITLE, "Hola, Good News");
            detailIntent.putExtra(DetailActivity.EXTRA_MESSAGE, "Now you can learn android in Dicoding");
            startActivity(detailIntent);
        }
    }

    @Override
    public void postAsync() {
        showNotification(MainActivity.this
                , "Hi, how are you?"
                , "Do you have any plan this weekend? Let's hangout"
                , 110);
    }

    private static class DelayAsync extends AsyncTask<Void, Void, Void> {

        WeakReference<AsyncCallback> callback;

        DelayAsync(AsyncCallback callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.get().postAsync();
        }
    }

    private void showNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Navigation channel";

        Intent notifDetailIntent = new Intent(this, DetailActivity.class);
        notifDetailIntent.putExtra(DetailActivity.EXTRA_TITLE, title);
        notifDetailIntent.putExtra(DetailActivity.EXTRA_MESSAGE, message);

        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addParentStack(DetailActivity.class)
                .addNextIntent(notifDetailIntent)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notifManaCompact = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_email_black_24dp)
                .setColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID
                    , CHANNEL_NAME
                    , NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notifManaCompact != null) {
                notifManaCompact.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notifManaCompact != null) {
            notifManaCompact.notify(notifId, notification);
        }
    }


}

interface AsyncCallback {
    void postAsync();
}
