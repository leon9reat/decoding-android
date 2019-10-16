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

import com.medialink.submission5.Const;
import com.medialink.submission5.MainActivity;
import com.medialink.submission5.R;

import java.util.ArrayList;
import java.util.List;

public class MovieNotif {
    private Context context;
    private static MovieNotif sNotif;

    private int idNotif = 0;
    private final List<NotifItem> stackNotif = new ArrayList<>();
    private static final CharSequence CHANNEL_NAME = "movie channel";
    private final static String NOTIFICATION_GROUP = "movie_notif_group";
    private static final int MAX_NOTIFICATION = 2;

    public MovieNotif(Context context) {
        this.context = context;
    }

    public static MovieNotif getInstance(Context context) {
        if (sNotif == null) {
            sNotif = new MovieNotif(context);
        }
        return sNotif;
    }

    public void newNotif(NotifItem item) {
        stackNotif.add(item);
        sendNotif();
        idNotif++;
    }

    private void sendNotif() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_black_24dp);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("EXTRA", Const.NOTIFICATION_REQUEST_CODE);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                Const.NOTIFICATION_REQUEST_CODE,
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
            notificationManager.cancel(idNotif-1);

            String textInboxBesarDiatas = String.format("%s %s", idNotif, context.getString(R.string.notif_inbox_title));
            String textSummaryKecilDibawah = String.format("%s", context.getString(R.string.notif_inbox_summary));

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine(stackNotif.get(idNotif).getTitle())
                    .addLine(stackNotif.get(idNotif - 1).getTitle())
                    .setBigContentTitle(textInboxBesarDiatas)
                    .setSummaryText(textSummaryKecilDibawah);
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(textInboxBesarDiatas)
                    .setContentText(textSummaryKecilDibawah)
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
