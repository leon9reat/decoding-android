package com.medialink.decomovie.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.medialink.decomovie.DetailActivity;
import com.medialink.decomovie.MainActivity;
import com.medialink.decomovie.R;
import com.medialink.decomovie.adapter.RetrofitClient;
import com.medialink.decomovie.adapter.RetrofitInterface;
import com.medialink.decomovie.adapter.TvAdapter;
import com.medialink.decomovie.model.TvRespon;
import com.medialink.decomovie.model.TvShow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    private static final String STATE_LIST = "STATE_LIST";
    public static final String TV = "TV";
    private RecyclerView rvTv;
    private List<TvShow> listTv;


    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTv = view.findViewById(R.id.rv_tv);

        if (savedInstanceState == null) {
            addDataTv();
            Log.d("debug", "tv load data baru");
        } else {
            //title = savedInstanceState.getString(STATE_TITLE);
            List<TvShow> stateList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            //int stateMode = savedInstanceState.getInt(STATE_MODE);
            if (stateList != null) {
                listTv = stateList;
                loadDataTv(listTv);
                Log.d("debug", "tv load data lama");
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(STATE_LIST, (ArrayList<? extends Parcelable>) listTv);
    }

    public void addDataTv() {
        RetrofitInterface service = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);

        // setting bahasa berubah
        String lang =  Locale.getDefault().getLanguage();
        if (lang.equals("in")) {
            lang = "id-ID";
        } else if (lang.equals("en")) {
            lang = "en-US";
        }
        Call<TvRespon> call = service.getTvShow(1, lang);
        call.enqueue(new Callback<TvRespon>() {
            @Override
            public void onResponse(Call<TvRespon> call, Response<TvRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listTv = response.body().getResults();
                        loadDataTv(listTv);
                    }
                }
            }

            @Override
            public void onFailure(Call<TvRespon> call, Throwable t) {
                Toast.makeText(getContext(), "Unable to load tv show", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataTv(List<TvShow> list) {
        TvAdapter tvAdapter = new TvAdapter(list);
        rvTv.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTv.setAdapter(tvAdapter);
        tvAdapter.setTvCallback(new TvAdapter.TvCallback() {
            @Override
            public void onItemClicked(TvShow data) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(MainActivity.TYPE_LIST, 102);
                intent.putExtra(TV, data);
                startActivity(intent);
            }
        });
    }
}
