package com.medialink.submission3.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medialink.submission3.model.movie.CastItem;
import com.medialink.submission3.model.movie.MovieDetailRespon;
import com.medialink.submission3.model.movie.MovieItem;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private MutableLiveData<ArrayList<MovieItem>> listMovies = new MutableLiveData<>();
    private MutableLiveData<MovieDetailRespon> movieDetail = new MutableLiveData<>();
    private MutableLiveData<ArrayList<CastItem>> listMovieCast = new MutableLiveData<>();

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
    public LiveData<ArrayList<CastItem>> getMovieCast() {
        return listMovieCast;
    }

    public void postMovieCast(ArrayList<CastItem> listCast) {
        this.listMovieCast.postValue(listCast);
    }
}
