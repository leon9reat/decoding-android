package com.medialink.submission5.contract;

import com.medialink.submission5.model.DetailModel;

public interface DetailContract {

    interface DetailInterface {
        void getMovie();
        void getTv();
        void showData(DetailModel data);
        void showLoading(Boolean state);
        void setError(String msg);
        void showMessage(String msg);
    }

    interface PresenterDetailInterface {
        void setDetailView(DetailInterface detailView);
        void getMovie(int movieId);
        void getTv(int tvId);
        void clickFavorite(int movieId);
        void clickRate(int movieId);
    }

}
