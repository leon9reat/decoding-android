package com.medialink.submission5.contract;

import android.content.Context;

import com.medialink.submission5.model.local.FavoriteItem;
import com.medialink.submission5.model.movie.MovieResult;

import java.util.ArrayList;
import java.util.List;

public interface FavoriteContract {

    interface FavoriteInterface {
        void showDetail(int detailType, int id);
    }

    interface MovieFavInterface {
        void showLoading(Boolean state);
        void showMovie(List<FavoriteItem> list);
        void listClick(FavoriteItem movie, int position);
        void itemDelete(FavoriteItem movie, int position);
    }

    interface TvFavInterface {
    }

    interface PresenterFavInterface {
        void setDatabase(Context context);
        void setFavMovieView(MovieFavInterface movieView);
        void setFavTvView(TvFavInterface tvView);
        void setFavView(FavoriteInterface mainView);
        void getMovie();
        void deleteMovie(FavoriteItem item);
        void getTv();
    }

}