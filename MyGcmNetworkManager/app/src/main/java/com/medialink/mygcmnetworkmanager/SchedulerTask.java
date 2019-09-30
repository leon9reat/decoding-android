package com.medialink.mygcmnetworkmanager;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class SchedulerTask {
    private GcmNetworkManager mGcmNetManager;

    SchedulerTask(Context context) {
        mGcmNetManager = GcmNetworkManager.getInstance(context);
    }

    void createPeriodicTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setService(SchedulerService.class)
                .setPeriod(69)
                .setFlex(10)
                .setTag(SchedulerService.TAG_TASK_WEATHER_LOG)
                .setPersisted(true)
                .build();
        mGcmNetManager.schedule(periodicTask);
    }

    void CancelPeriodicTask() {
        if (mGcmNetManager != null) {
            mGcmNetManager.cancelTask(SchedulerService.TAG_TASK_WEATHER_LOG
                    , SchedulerService.class);
        }
    }
}
