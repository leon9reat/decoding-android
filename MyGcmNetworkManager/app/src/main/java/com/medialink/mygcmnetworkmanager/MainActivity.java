package com.medialink.mygcmnetworkmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SchedulerTask mSchedulerTask;

    @BindView(R.id.btn_set_scheduler)
    Button btnSetScheduler;
    @BindView(R.id.btn_cancel_scheduler)
    Button btnCancelScheduler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSchedulerTask = new SchedulerTask(this);
    }

    @OnClick({R.id.btn_set_scheduler, R.id.btn_cancel_scheduler})
    public void onViewClicked(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_set_scheduler:
                mSchedulerTask.createPeriodicTask();
                Log.d(TAG, "onViewClicked: Periodic Task Created");
                break;
            case R.id.btn_cancel_scheduler:
                mSchedulerTask.CancelPeriodicTask();
                Log.d(TAG, "onViewClicked: Periodic Task Cancelled");
                break;
        }
    }
}
