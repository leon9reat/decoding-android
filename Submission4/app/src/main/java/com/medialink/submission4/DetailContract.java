package com.medialink.submission4;

import android.content.Context;

import com.medialink.submission4.model.movie.MovieDetailRespon;
import com.medialink.submission4.model.tv.TvDetailRespon;

public interface DetailContract {

    interface ViewInterface {
        void setMovie(MovieDetailRespon movieDetail);
        void setError(String msg);
        void showMessage(String msg);
        void showLoading(Boolean state);
        void setButtonFavoriteColor(boolean isFavorite);
    }

    interface PresenterInterface {
        void getMovieDetail(int id);
        void getMovieCredit(int id);
        void getTvDetail(int id);
        void getTvCredit(int id);
        void checkFavorite(Context context, int id, int typeId);
        void changeStateFavoriteMovie(Context context, final MovieDetailRespon movie);
        void changeStateFavoriteTv(Context context, final TvDetailRespon tv);
    }
}
