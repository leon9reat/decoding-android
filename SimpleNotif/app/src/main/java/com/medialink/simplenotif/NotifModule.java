package com.medialink.simplenotif;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NotifModule {

    Context context;

    NotifModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    NotificationManager provideNotifManager() {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    PendingIntent providePendingIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"));
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    @Provides
    @Singleton
    Notification provideNotifBuilder(NotificationManager manager, PendingIntent pendingIntent) {
        String CHANNEL_ID = "channel_1";
        CharSequence CHANNEL_NAME = "dicoding channel";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_white_24dp))
                .setContentTitle(context.getResources().getString(R.string.content_title))
                .setContentText(context.getResources().getString(R.string.content_text))
                .setSubText(context.getResources().getString(R.string.subtext))
                .setAutoCancel(true);

        // Untuk android Oreo ke atas perlu menambahkan notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        return mBuilder.build();
    }
}
