package com.medialink.submission5.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medialink.submission5.contract.MainContract;
import com.medialink.submission5.model.movie.MovieRespon;
import com.medialink.submission5.model.movie.MovieResult;
import com.medialink.submission5.model.network.ApiInterface;
import com.medialink.submission5.model.network.RetrofitClient;
import com.medialink.submission5.model.tv.TvResponse;
import com.medialink.submission5.model.tv.TvResult;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieViewModel extends ViewModel {

    private static final String TAG = "MovieViewModel";

    private int mMoviePage = 1;
    private MutableLiveData<List<MovieResult>> movieList;
    private MainContract.MovieInterface mViewMovie;

    private int mTvPage = 1;
    private MutableLiveData<List<TvResult>> tvList;
    private MainContract.TvInterface mViewTv;

    public LiveData<List<MovieResult>> getMovieList(final MainContract.MovieInterface viewInterface) {
        if (movieList == null) {
            this.mViewMovie = viewInterface;
            movieList = new MutableLiveData<>();
            loadMovieList();
        }

        return movieList;
    }

    public void setMovie() {
        mMoviePage = 1;
        loadMovieList();
    }

    public void setMovieFilter(String s) {
        mViewMovie.showLoading(true);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiInterface api = retrofit.create(ApiInterface.class);

        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<MovieRespon> call = api.getMovieFilter(mMoviePage, lang, s);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(Call<MovieRespon> call, Response<MovieRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        movieList.setValue(response.body().getResults());
                        mViewMovie.showLoading(false);
                    }
                } else {
                    mViewMovie.showLoading(false);
                    mViewMovie.setError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieRespon> call, Throwable t) {
                mViewMovie.showLoading(false);
                mViewMovie.setError("Fatal Error: " + t.getMessage());
            }
        });
    }

    public void setMovieRelease(String drTgl, String spTgl) {
        mViewMovie.showLoading(true);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiInterface api = retrofit.create(ApiInterface.class);

        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<MovieRespon> call = api.getMovieRelease(mMoviePage, lang, drTgl, spTgl);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(Call<MovieRespon> call, Response<MovieRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        movieList.setValue(response.body().getResults());
                        mViewMovie.showLoading(false);
                    }
                } else {
                    mViewMovie.showLoading(false);
                    mViewMovie.setError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieRespon> call, Throwable t) {
                mViewMovie.showLoading(false);
                mViewMovie.setError("Fatal Error: " + t.getMessage());
            }
        });
    }

    private void loadMovieList() {
        mViewMovie.showLoading(true);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiInterface api = retrofit.create(ApiInterface.class);

        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<MovieRespon> call = api.getMovie(mMoviePage, lang);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(Call<MovieRespon> call, Response<MovieRespon> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        movieList.setValue(response.body().getResults());
                        mViewMovie.showLoading(false);
                    }
                } else {
                    mViewMovie.showLoading(false);
                    mViewMovie.setError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieRespon> call, Throwable t) {
                mViewMovie.showLoading(false);
                mViewMovie.setError("Fatal Error: " + t.getMessage());
            }
        });
    }

    public LiveData<List<TvResult>> getTvList(final MainContract.TvInterface viewInterface) {
        if (tvList == null) {
            this.mViewTv = viewInterface;
            tvList = new MutableLiveData<>();
            loadTvList();
        }

        return tvList;
    }

    public void setTv() {
        mTvPage = 1;
        loadTvList();
    }

    public void loadTvList() {
        mViewTv.showLoading(true);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiInterface api = retrofit.create(ApiInterface.class);

        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<TvResponse> call = api.getTv(mTvPage, lang);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        tvList.setValue(response.body().getResults());
                        mViewTv.showLoading(false);
                    }
                } else {
                    mViewTv.showLoading(false);
                    mViewTv.setError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                mViewTv.showLoading(false);
                mViewTv.setError("Fatal Error: " + t.getMessage());
            }
        });
    }

    public void setTvFilter(String s) {
        mViewTv.showLoading(true);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiInterface api = retrofit.create(ApiInterface.class);

        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<TvResponse> call = api.getTvFilter(mTvPage, lang, s);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        tvList.setValue(response.body().getResults());
                        mViewTv.showLoading(false);
                    }
                } else {
                    mViewTv.showLoading(false);
                    mViewTv.setError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                mViewTv.showLoading(false);
                mViewTv.setError("Fatal Error: " + t.getMessage());
            }
        });
    }

    public void setTvRelease(String drTgl, String spTgl) {
        mViewTv.showLoading(true);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiInterface api = retrofit.create(ApiInterface.class);

        String lang = "en-US";
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("in")) lang = "id-ID";

        Call<TvResponse> call = api.getTvRelease(mTvPage, lang, drTgl, spTgl);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        tvList.setValue(response.body().getResults());
                        mViewTv.showLoading(false);
                    }
                } else {
                    mViewTv.showLoading(false);
                    mViewTv.setError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                mViewTv.showLoading(false);
                mViewTv.setError("Fatal Error: " + t.getMessage());
            }
        });
    }
}
