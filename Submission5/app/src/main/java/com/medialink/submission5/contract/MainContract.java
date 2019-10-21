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
        void getMovie();
        void getMovieFilter(String s);
        void getMovieRelease(String drTgl, String spTgl);
        void showMovie(ArrayList<MovieResult> list);
        void listClick(MovieResult movie, int position);
        void showLoading(Boolean state);
        void setError(String msg);
        void showMessage(String msg);
    }

    interface TvInterface {
        void getTv();
        void getTvFilter(String s);
        void getTvRelease(String drTgl, String spTgl);
        void showTv(ArrayList<TvResult> list);
        void listClick(TvResult tv, int position);
        void showLoading(Boolean state);
        void setError(String msg);
        void showMessage(String msg);
    }

    interface PresenterInterface {
        void setMovieView(MovieInterface movieView);
        void setTvView(TvInterface tvView);
        void setMainView(MainInterface mainView);
        void getMovie();
        void getMovieFilter(String s);
        void getMovieRelase(String drTgl, String spTgl);
        void getTv();
        void getTvFilter(String s);
        void getTvRelease(String drTgl, String spTgl);
    }

}
