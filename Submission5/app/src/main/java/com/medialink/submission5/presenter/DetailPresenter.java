package com.medialink.submission5.presenter;

import com.medialink.submission5.contract.DetailContract;

public class DetailPresenter implements DetailContract.PresenterDetailInterface {

    private static final String TAG = "DetailPresenter";
    private static DetailPresenter sInstance;
    private DetailContract.DetailInterface mDetailView;

    public DetailPresenter() {
    }

    public static DetailPresenter getInstance() {
        if (sInstance == null) {
            sInstance = new DetailPresenter();
        }
        return sInstance;
    }

    @Override
    public void setDetailView(DetailContract.DetailInterface detailView) {

    }

    @Override
    public void getMovie(int movieId) {

    }

    @Override
    public void getTv(int tvId) {

    }

    @Override
    public void clickFavorite(int movieId) {

    }

    @Override
    public void clickRate(int movieId) {

    }

}
