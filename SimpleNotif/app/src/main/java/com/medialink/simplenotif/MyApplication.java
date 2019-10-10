package com.medialink.simplenotif;

import android.app.Application;

public class MyApplication extends Application {
    private NotifComponent mNotifComponent;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public NotifComponent getComponent() {
        return mNotifComponent;
    }
}
