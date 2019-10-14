package com.medialink.submission5.contract;

import com.medialink.submission5.model.movie.MovieResult;
import com.medialink.submission5.model.tv.TvResult;

import java.util.ArrayList;

public interface MainContract {

    interface MainInterface {
        void showSettings();

        void showDetail(int detailType, int id);

        void showFavorite();

        void changeLanguage();
    }


    interface MovieInterface {
        void showMovie(ArrayList<MovieResult> list);

        void listClick(MovieResult movie, int position);

        void showLoading(Boolean state);
    }

    interface TvInterface {
        void showTv(ArrayList<TvResult> list);

        void listClick(TvResult tv, int position);

        void showLoading(Boolean state);
    }

    interface PresenterInterface {
        void setMovieView(MovieInterface movieView);

        void setTvView(TvInterface tvView);

        void setMainView(MainInterface mainView);

        void getMovie(int page);

        void getMovieFilter(String s);

        void getTv(int page);

        void getTvFilter(String s);
    }

}
