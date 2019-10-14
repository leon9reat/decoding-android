package com.medialink.submission5.presenter;

import com.medialink.submission5.contract.MainContract;
import com.medialink.submission5.model.movie.MovieResult;
import com.medialink.submission5.model.tv.TvResult;

import java.util.ArrayList;

public class MainPresenter implements MainContract.PresenterInterface {

    private static final String TAG = "MainPresenter";
    private static MainPresenter sInstance;
    private MainContract.MovieInterface mMovieView;
    private MainContract.TvInterface mTvView;
    private MainContract.MainInterface mMainView;

    public MainPresenter() {
    }

    public static MainPresenter getInstance() {
        if (sInstance == null) {
            sInstance = new MainPresenter();
        }
        return sInstance;
    }

    @Override
    public void setMainView(MainContract.MainInterface mMainView) {
        this.mMainView = mMainView;
    }

    @Override
    public void setMovieView(MainContract.MovieInterface mMovieView) {
        this.mMovieView = mMovieView;
    }

    @Override
    public void setTvView(MainContract.TvInterface tvView) {
        this.mTvView = tvView;
    }

    @Override
    public void getMovie(int page) {
        mMovieView.showLoading(true);

        final ArrayList<MovieResult> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MovieResult movie = new MovieResult();
            movie.setTitle("Title " + i);
            movie.setReleaseDate("Date Release " + i);
            movie.setOverview("Overview Overview Overview Overview Overview Overview Overview " + i);

            list.add(movie);
        }

        mMovieView.showMovie(list);
    }

    @Override
    public void getMovieFilter(String s) {
        mMovieView.showLoading(true);

        final ArrayList<MovieResult> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            MovieResult movie = new MovieResult();
            movie.setTitle(s + " Filter Title " + i);
            movie.setReleaseDate(s + " Filter Date Release " + i);
            movie.setOverview(s + "Filter Overview Filter Overview Filter Overview Filter Overview Filter Overview Overview Overview " + i);

            list.add(movie);
        }

        mMovieView.showMovie(list);
    }

    @Override
    public void getTv(int page) {
        mTvView.showLoading(true);

        final ArrayList<TvResult> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TvResult tv = new TvResult();
            tv.setName("Judul Tv Show " + i);
            tv.setFirstAirDate("Airing Date " + i);
            tv.setOverview("Overview Overview Overview Overview Overview Overview Overview " + i);

            list.add(tv);
        }

        mTvView.showTv(list);
    }

    @Override
    public void getTvFilter(String s) {
        mTvView.showLoading(true);

        final ArrayList<TvResult> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            TvResult tv = new TvResult();
            tv.setName(s + " Filter Tv " + i);
            tv.setFirstAirDate(s + " Filter Airing Date " + i);
            tv.setOverview(s + "Filter Overview Filter Overview Filter Overview Filter Overview Filter Overview Overview Overview " + i);

            list.add(tv);
        }

        mTvView.showTv(list);
    }
}
