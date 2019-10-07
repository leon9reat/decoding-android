package com.medialink.submission4;

import com.medialink.submission4.model.tv.TvItem;

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
