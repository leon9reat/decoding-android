package com.medialink.submission3.presenter;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.medialink.submission3.DetailContract;
import com.medialink.submission3.model.ApiInterface;
import com.medialink.submission3.model.MainViewModel;
import com.medialink.submission3.model.RetrofitClient;
import com.medialink.submission3.model.movie.CastItem;
import com.medialink.submission3.model.movie.MovieCreditRespon;
import com.medialink.submission3.model.movie.MovieDetailRespon;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements DetailContract.PresenterInterface {

    private static final String TAG = DetailPresenter.class.getSimpleName();
    private DetailContract.ViewInterface mViewInterface;
    private MainViewModel mModel;

    // constructor
    public DetailPresenter(FragmentActivity activity, DetailContract.ViewInterface mViewInterface) {
        this.mViewInterface = mViewInterface;
        this.mModel = ViewModelProviders.of(activity).get(MainViewModel.class);
    }

    @Override
    public void getMovieDetail(int id) {
        mViewInterface.showLoading(true);

        ApiInterface service = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        // setting bahasa
        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<MovieDetailRespon> call = service.getMovieDetail(id, lang);
        call.enqueue(new Callback<MovieDetailRespon>() {
            @Override
            public void onResponse(Call<MovieDetailRespon> call, Response<MovieDetailRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        MovieDetailRespon movieDetail = response.body();
                        mModel.postMovieDetail(movieDetail);
                        //mView.setMovie(list);
                        mViewInterface.showLoading(false);

                        Log.d(TAG, "onResponse: "+movieDetail.getTitle());
                    }
                } else {
                    mViewInterface.showLoading(false);
                    mViewInterface.setError(response.message());
                    Log.d(TAG, "onResponse: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieDetailRespon> call, Throwable t) {
                mViewInterface.showLoading(false);
                mViewInterface.setError(t.getMessage());
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void getMovieCredit(int id) {
        mViewInterface.showLoading(true);

        ApiInterface service = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<MovieCreditRespon> call = service.getMovieCredit(id);
        call.enqueue(new Callback<MovieCreditRespon>() {
            @Override
            public void onResponse(Call<MovieCreditRespon> call, Response<MovieCreditRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        MovieCreditRespon movieCredit = response.body();
                        mModel.postMovieCast((ArrayList<CastItem>) movieCredit.getCast());
                        mViewInterface.showLoading(false);
                        Log.d(TAG, "onResponse: "+movieCredit.getId());
                    }
                } else {
                    mViewInterface.showLoading(false);
                    mViewInterface.setError(response.message());
                    Log.d(TAG, "onResponse: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieCreditRespon> call, Throwable t) {
                mViewInterface.showLoading(false);
                mViewInterface.setError(t.getMessage());
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
