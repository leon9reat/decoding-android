package com.medialink.submission4.presenter;

import android.util.Log;

import com.medialink.submission4.MovieContract;
import com.medialink.submission4.model.ApiInterface;
import com.medialink.submission4.model.RetrofitClient;
import com.medialink.submission4.model.movie.MovieItem;
import com.medialink.submission4.model.movie.MovieRespon;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresenter implements MovieContract.PresenterInterface {

    private static final String TAG = MoviePresenter.class.getSimpleName();
    private MovieContract.ViewInterface mView;

    public MoviePresenter(MovieContract.ViewInterface mView) {
        this.mView = mView;
    }

    @Override
    public void getMovies(int page) {
        ApiInterface service = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        // setting bahasa
        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<MovieRespon> call = service.getMovie(page, lang);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(Call<MovieRespon> call, Response<MovieRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<MovieItem> list = new ArrayList<>();
                        list.addAll(response.body().getResults());
                        mView.setMovie(list);
                        Log.d(TAG, "onResponse: "+list.size());
                    }
                } else {
                    mView.setError(response.message());
                    Log.d(TAG, "onResponse: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieRespon> call, Throwable t) {
                mView.setError(t.getMessage());
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
