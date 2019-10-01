package com.medialink.submission3;

import com.medialink.submission3.model.movie.MovieItem;

import java.util.ArrayList;

public interface MovieContract {
    interface ViewInterface {
        void setMovie(ArrayList<MovieItem> list);
        void itemClick(MovieItem movie, int position);
        void setError(String msg);
        void showMessage(String msg);
    }

    interface PresenterInterface {
        void getMovies(int page);
    }
}
