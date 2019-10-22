package com.medialink.sub5consumer.presenter;

import com.medialink.sub5consumer.contract.DetailContract;

public class DetailPresenter implements DetailContract.PresenterDetailInterface {

    private static final String TAG = "DetailPresenter";
    private static DetailPresenter sInstance;
    private DetailContract.DetailInterface mDetailView;

    public static DetailPresenter getInstance() {
        if (sInstance == null) {
            sInstance = new DetailPresenter();
        }
        return sInstance;
    }

    @Override
    public void setDetailView() {

    }

    @Override
    public void getMovie() {

    }

    @Override
    public void getTv() {

    }

    @Override
    public void clickFavorite() {

    }

    @Override
    public void clickRate() {

    }
}
