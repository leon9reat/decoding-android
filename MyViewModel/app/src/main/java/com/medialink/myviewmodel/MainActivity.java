package com.medialink.myviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private WeatherAdapter mAdapter;
    private EditText etCity;
    private ProgressBar progressBar;
    private MainViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new WeatherAdapter();
        mAdapter.notifyDataSetChanged();


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        etCity = findViewById(R.id.et_city);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        mModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mModel.getWeathers().observe(this, getWeather);

        findViewById(R.id.btn_city).setOnClickListener(myListener);
    }

    private Observer<ArrayList<WeatherItems>> getWeather = new Observer<ArrayList<WeatherItems>>() {
        @Override
        public void onChanged(ArrayList<WeatherItems> weatherItems) {
            if (weatherItems != null) {
                mAdapter.setData(weatherItems);
                showLoading(false);
            }
        }
    };

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String city = etCity.getText().toString();
            if (TextUtils.isEmpty(city)) return;
            mModel.setWeather(city);
            showLoading(true);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
