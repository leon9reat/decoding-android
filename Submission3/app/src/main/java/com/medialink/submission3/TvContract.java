package com.medialink.submission3;

import com.medialink.submission3.model.tv.TvItem;

public interface TvContract {

    interface ViewInterface {
        void itemClick(TvItem tv, int position);
        void setError(String msg);
        void showMessage(String msg);
        void showLoading(Boolean state);
    }

    interface PresenterInterface {
        void getTv(int page);
    }
}
