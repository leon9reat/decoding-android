package com.medialink.submission5.contract;

import android.content.Context;

import com.medialink.submission5.model.local.FavoriteItem;

import java.util.List;

public interface FavoriteContract {

    interface FavoriteInterface {
        void showDetail(int detailType, int id);
        void refreshWidget();
    }

    interface MovieFavInterface {
        void showLoading(Boolean state);
        void showMovie(List<FavoriteItem> list);
        void listClick(FavoriteItem movie, int position);
        void itemDelete(FavoriteItem movie, int position);
    }

    interface TvFavInterface {
        void showLoading(Boolean state);
        void showTv(List<FavoriteItem> list);
        void listClick(FavoriteItem movie, int position);
        void itemDelete(FavoriteItem movie, int position);
    }

    interface PresenterFavInterface {
        void setDatabase(Context context);
        void setFavMovieView(MovieFavInterface movieView);
        void setFavTvView(TvFavInterface tvView);
        void setFavView(FavoriteInterface mainView);
        void getMovie();
        void getMovieProvider(Context context);
        void deleteMovie(FavoriteItem item);
        void getTv();
        void getTvProvider(Context context);
        void deleteTv(FavoriteItem item);
        void refreshWidget();
    }

}
