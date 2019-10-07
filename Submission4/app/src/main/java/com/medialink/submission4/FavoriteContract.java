package com.medialink.submission4;

import android.content.Context;

import com.medialink.submission4.model.FavoriteItem;
import com.medialink.submission4.model.movie.MovieItem;

import java.util.ArrayList;

public interface FavoriteContract {
    interface ViewInterface {
        void setMovie(ArrayList<MovieItem> list);
        void itemClick(FavoriteItem movie, int position);
        void itemDelete(String id, int position);
        void setError(String msg);
        void showMessage(String msg);
        void refreshAddItem(FavoriteItem favItem);
        void refreshUpdateItem(int position, FavoriteItem favItem);
        void refreshRemoveItem(int position);

    }

    interface PresenterInterface {
        void getMovies(int page);
        void clickFavorite(boolean isEdit, MovieItem movie, String id);
        void itemDelete(String id, int position);
    }
}
