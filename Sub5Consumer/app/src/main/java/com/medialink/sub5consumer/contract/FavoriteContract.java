package com.medialink.sub5consumer.contract;

import android.content.Context;

import com.medialink.sub5consumer.model.local.FavoriteItem;

import java.util.List;

public interface FavoriteContract {

    interface FavoriteInterface {
        void showDetail(int detailType, int id, FavoriteItem item);
    }

    interface MovieFavInterface {
        void showLoading(Boolean state);
        void showMovie(List<FavoriteItem> list);
        void listClick(FavoriteItem movie);
        void itemDelete(FavoriteItem movie, int position);
    }

    interface TvFavInterface {
        void showLoading(Boolean state);
        void showTv(List<FavoriteItem> list);
        void listClick(FavoriteItem tv);
        void itemDelete(FavoriteItem tv, int position);
    }

    interface PresenterFavInterface {
        void setDatabase();
        void setFavMovieView(MovieFavInterface movieView);
        void setFavTvView(TvFavInterface tvView);
        void setFavView(FavoriteInterface mainView);
        void getMovie();
        void getMovieProvider(Context context);
        void deleteMovie();
        void getTv();
        void getTvProvider(Context context);
        void deleteTv();
    }
}
