package com.medialink.submission4.presenter;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.medialink.submission4.TvContract;
import com.medialink.submission4.model.ApiInterface;
import com.medialink.submission4.model.MainViewModel;
import com.medialink.submission4.model.RetrofitClient;
import com.medialink.submission4.model.tv.TvItem;
import com.medialink.submission4.model.tv.TvResponse;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvPresenter implements TvContract.PresenterInterface {

    private static final String TAG = TvPresenter.class.getSimpleName();
    private TvContract.ViewInterface mView;
    private MainViewModel mModel;

    // constructor
    public TvPresenter(FragmentActivity activity, TvContract.ViewInterface mView) {
        this.mView = mView;
        this.mModel = ViewModelProviders.of(activity).get(MainViewModel.class);
    }

    @Override
    public void getTv(int page) {
        mView.showLoading(true);
        ApiInterface service = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        // setting bahasa
        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<TvResponse> call = service.getTvShow(page, lang);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<TvItem> list = new ArrayList<>();
                        list.addAll(response.body().getResults());
                        mModel.postListMovie(list);
                        mView.showLoading(false);

                        Log.d(TAG, "onResponse: "+list.size());
                    }
                } else {
                    mView.showLoading(false);
                    mView.setError(response.message());
                    Log.d(TAG, "onResponse: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                mView.showLoading(false);
                mView.setError(t.getMessage());
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
