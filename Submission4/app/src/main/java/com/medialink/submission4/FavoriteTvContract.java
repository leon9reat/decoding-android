package com.medialink.submission4;

import com.medialink.submission4.model.FavoriteItem;
import com.medialink.submission4.model.movie.MovieItem;
import com.medialink.submission4.model.tv.TvItem;

import java.util.ArrayList;

public interface FavoriteTvContract {
    interface ViewInterface {
        void setMovie(ArrayList<MovieItem> list);
        void itemFavoriteClick(FavoriteItem tv, int position);
        void itemDelete(String id, int position);
        void setError(String msg);
        void showMessage(String msg);
        void refreshAddItem(FavoriteItem favItem);
        void refreshUpdateItem(int position, FavoriteItem favItem);
        void refreshRemoveItem(int position);

    }

    interface PresenterInterface {
        void getTv(int page);
        void clickFavorite(boolean isEdit, TvItem movie, String id);
        void itemDelete(String id, int position);
    }
}
