package com.medialink.submission3.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medialink.submission3.model.movie.MovieItem;
import com.medialink.submission3.model.movie.MovieRespon;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<MovieItem>> listMovies = new MutableLiveData<>();

    public LiveData<ArrayList<MovieItem>> getMovies() {
        return listMovies;
    }

    public void setMovies(int page) {
        ApiInterface service = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        // setting bahasa
        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<MovieRespon> call = service.getMovie(1, lang);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(Call<MovieRespon> call, Response<MovieRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: "+response.body().getResults().toString());
                    }
                } else {
                    Log.d(TAG, "onResponse: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieRespon> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
