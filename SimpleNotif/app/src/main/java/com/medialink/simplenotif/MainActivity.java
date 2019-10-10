package com.medialink.simplenotif;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    NotificationManager manager;
    @Inject
    Notification notification;

    public static final int NOTIFICATION_ID = 1;
    private NotifComponent mNotifComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotifComp = DaggerNotifComponent.builder()
                .notifModule(new NotifModule(this))
                .build();
        mNotifComp.inject(this);
    }

    public void sendNotification(View view) {
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, notification);
        }
    }
}
