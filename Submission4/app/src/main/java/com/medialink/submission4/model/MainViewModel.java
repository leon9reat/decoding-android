package com.medialink.submission4.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medialink.submission4.model.movie.MovieCastItem;
import com.medialink.submission4.model.movie.MovieDetailRespon;
import com.medialink.submission4.model.movie.MovieItem;
import com.medialink.submission4.model.tv.TvCastItem;
import com.medialink.submission4.model.tv.TvDetailRespon;
import com.medialink.submission4.model.tv.TvItem;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ArrayList<MovieItem>> listMovies = new MutableLiveData<>();
    private MutableLiveData<MovieDetailRespon> movieDetail = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MovieCastItem>> listMovieCast = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvItem>> listTv = new MutableLiveData<>();
    private MutableLiveData<TvDetailRespon> tvDetail = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvCastItem>> listTvCast = new MutableLiveData<>();

    //* list movie
    public LiveData<ArrayList<MovieItem>> getMovies() {
        return listMovies;
    }

    public void setListMovies(ArrayList<MovieItem> listMovies) {
        this.listMovies.setValue(listMovies);
    }

    //* movie detail
    public LiveData<MovieDetailRespon> getMovieDetail() {
        return movieDetail;
    }

    public void postMovieDetail(MovieDetailRespon movieDetail) {
        this.movieDetail.postValue(movieDetail);
    }

    //* cast movie
    public LiveData<ArrayList<MovieCastItem>> getMovieCast() {
        return listMovieCast;
    }

    public void postMovieCast(ArrayList<MovieCastItem> listCast) {
        this.listMovieCast.postValue(listCast);
    }

    //* list tv
    public LiveData<ArrayList<TvItem>> getTv() {
        return listTv;
    }

    public void postListMovie(ArrayList<TvItem> listTv) {
        this.listTv.postValue(listTv);
    }

    //* tv detail
    public LiveData<TvDetailRespon> getTvDetail() {
        return tvDetail;
    }

    public void postTvDetail(TvDetailRespon tvDetail) {
        this.tvDetail.postValue(tvDetail);
    }

    //* cast tv
    public LiveData<ArrayList<TvCastItem>> getTvCast() {
        return listTvCast;
    }

    public void postTvCast(ArrayList<TvCastItem> listCast) {
        this.listTvCast.postValue(listCast);
    }
}
