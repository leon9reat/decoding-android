package com.medialink.mywidgets;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final int JOB_ID = 100;
    private static final int SCHEDULE_OF_PERIOD = 86000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btn_start);
        Button btnStop = findViewById(R.id.btn_stop);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_start:
                startJob();
                break;
            case R.id.btn_stop:
                cancelJob();
                break;
        }

    }

    private void startJob() {
        ComponentName mServiceComponent = new ComponentName(this, UpdateWidgetService.class);

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(900000L); // 15 menit
        } else {
            builder.setPeriodic(SCHEDULE_OF_PERIOD);
        }

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            scheduler.schedule(builder.build());
        }
        
        Toast.makeText(this, "Job service started", Toast.LENGTH_SHORT).show();
    }

    private void cancelJob() {
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (tm != null) {
            tm.cancel(JOB_ID);
        }
        
        Toast.makeText(this, "Job service canceled", Toast.LENGTH_SHORT).show();
    }
}
