package com.medialink.sub5consumer.contract;

import com.medialink.sub5consumer.model.local.FavoriteItem;

public interface DetailContract {

    interface DetailInterface {
        void getMovie();
        void getTv();
        void showData(FavoriteItem data);
        void showLoading(Boolean state);
        void setError(String msg);
        void showMessage(String msg);
    }

    interface PresenterDetailInterface {
        void setDetailView();
        void getMovie();
        void getTv();
        void clickFavorite();
        void clickRate();
    }

}
