package com.medialink.submission5.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.medialink.submission5.MainActivity;
import com.medialink.submission5.R;
import com.medialink.submission5.notification.NotifItem;

import java.util.ArrayList;
import java.util.List;

public class MovieNotif {
    private Context context;
    private int idNotif = 0;
    private final List<NotifItem> stackNotif = new ArrayList<>();
    private static final CharSequence CHANNEL_NAME = "movie channel";
    private final static String NOTIFICATION_GROUP = "movie_notif_group";
    private final static int NOTIFICATION_REQUEST_CODE = 200;
    private static final int MAX_NOTIFICATION = 2;

    public MovieNotif(Context context) {
        this.context = context;
    }

    public void sendNotif() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_black_24dp);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder;

        String CHANNEL_ID = "channel_01";
        if (idNotif < MAX_NOTIFICATION) {
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(stackNotif.get(idNotif).getTitle())
                    .setContentText(stackNotif.get(idNotif).getMessage())
                    .setSmallIcon(R.drawable.ic_notifications_active_white_24dp)
                    .setLargeIcon(largeIcon)
                    .setGroup(NOTIFICATION_GROUP)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine(stackNotif.get(idNotif).getTitle())
                    .addLine(stackNotif.get(idNotif-1).getTitle())
                    .setBigContentTitle(idNotif + " Movie Updates")
                    .setSummaryText("Check it out");
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(idNotif + " Movie Updates")
                    .setContentText("Check it out")
                    .setSmallIcon(R.drawable.ic_notifications_active_white_24dp)
                    .setGroup(NOTIFICATION_GROUP)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            mBuilder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (notificationManager != null) {
            notificationManager.notify(idNotif, notification);
        }
    }

    public void clearNotif() {
        stackNotif.clear();
        idNotif = 0;
    }
}