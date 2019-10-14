package com.medialink.submission5.presenter;

import android.content.Context;

import androidx.room.Room;

import com.medialink.submission5.Const;
import com.medialink.submission5.contract.FavoriteContract;
import com.medialink.submission5.model.local.AppDatabase;
import com.medialink.submission5.model.local.FavoriteDao;
import com.medialink.submission5.model.local.FavoriteItem;

import java.util.List;

public class FavoritePresenter implements FavoriteContract.PresenterFavInterface {

    private static final String TAG = "FavoritePresenter";
    private static FavoritePresenter sInstance;
    private FavoriteContract.FavoriteInterface mFavView;
    private FavoriteContract.MovieFavInterface mFavMovie;
    private FavoriteContract.TvFavInterface mFavTv;

    private AppDatabase database;
    private FavoriteDao favDao;

    public FavoritePresenter() {
    }

    public static FavoritePresenter getInstance() {
        if (sInstance == null) {
            sInstance = new FavoritePresenter();
        }
        return sInstance;
    }

    @Override
    public void setDatabase(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, "movie_db")
                .allowMainThreadQueries()
                .build();
        favDao = database.getFavoriteDao();
    }

    @Override
    public void setFavMovieView(FavoriteContract.MovieFavInterface movieView) {
        this.mFavMovie = movieView;
    }

    @Override
    public void setFavTvView(FavoriteContract.TvFavInterface tvView) {
        this.mFavTv = tvView;
    }

    @Override
    public void setFavView(FavoriteContract.FavoriteInterface favView) {
        this.mFavView = favView;
    }

    @Override
    public void getMovie() {
        mFavMovie.showLoading(true);
        List<FavoriteItem> items = favDao.getFavoriteByType(Const.DETAIL_MOVIE);
        mFavMovie.showMovie(items);
    }

    @Override
    public void deleteMovie(FavoriteItem item) {
        mFavMovie.showLoading(true);
        favDao.delete(item);
        mFavMovie.showLoading(false);
    }


    @Override
    public void getTv() {

    }
}
