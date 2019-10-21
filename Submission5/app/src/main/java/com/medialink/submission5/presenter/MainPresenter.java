package com.medialink.submission5.presenter;

import com.medialink.submission5.contract.MainContract;

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
    public void getMovie() {
        mMovieView.getMovie();
    }

    @Override
    public void getMovieFilter(String s) {
        mMovieView.getMovieFilter(s);
    }

    @Override
    public void getMovieRelase(String drTgl, String spTgl) {
        mMovieView.getMovieRelease(drTgl, spTgl);
    }

    @Override
    public void getTv() {
        mTvView.getTv();
    }

    @Override
    public void getTvFilter(String s) {
        mTvView.getTvFilter(s);
    }

    @Override
    public void getTvRelease(String drTgl, String spTgl) {
        mTvView.getTvRelease(drTgl, spTgl);
    }


}
