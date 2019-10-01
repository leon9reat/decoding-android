package com.medialink.submission3;

import com.medialink.submission3.model.movie.MovieDetailRespon;

public interface DetailContract {

    interface ViewInterface {
        void setMovie(MovieDetailRespon movieDetail);
        void setError(String msg);
        void showMessage(String msg);
        void showLoading(Boolean state);
    }

    interface PresenterInterface {
        void getMovieDetail(int id);
        void getMovieCredit(int id);
    }
}
