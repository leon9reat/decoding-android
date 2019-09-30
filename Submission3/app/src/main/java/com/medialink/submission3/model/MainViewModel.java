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

    public void setListMovies(ArrayList<MovieItem> listMovies) {
        this.listMovies.setValue(listMovies);
    }
}
