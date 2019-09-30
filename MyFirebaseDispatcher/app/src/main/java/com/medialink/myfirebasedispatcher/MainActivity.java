package com.medialink.myfirebasedispatcher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String DISPATCHER_TAG = "mydispatcher";
    private String CITY = "Pontianak";

    @BindView(R.id.btn_set_scheduler)
    Button btnSetScheduler;
    @BindView(R.id.btn_cancel_scheduler)
    Button btnCancelScheduler;

    FirebaseJobDispatcher mDispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Driver driver;
        mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
    }

    @OnClick({R.id.btn_set_scheduler, R.id.btn_cancel_scheduler})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_set_scheduler:
                startDispatcher();
                Log.d(TAG, "onViewClicked: Dispatcher Created");
                break;
            case R.id.btn_cancel_scheduler:
                cancelDispatcher();
                Log.d(TAG, "onViewClicked: Dispatcher Cancelled");
                break;
        }
    }

    public void startDispatcher() {
        Bundle myExtrasBundle = new Bundle();
        myExtrasBundle.putString(MyJobService.EXTRAS_CITY, CITY);

        Job myJob = mDispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag(DISPATCHER_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.executionWindow(0, 60))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK
                        //, Constraint.DEVICE_CHARGING
                )
                .setExtras(myExtrasBundle)
                .build();

        mDispatcher.mustSchedule(myJob);
    }

    public void cancelDispatcher() {
        mDispatcher.cancel(DISPATCHER_TAG);
    }
}
