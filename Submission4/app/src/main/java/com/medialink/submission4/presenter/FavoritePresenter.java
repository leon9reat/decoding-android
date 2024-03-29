package com.medialink.submission4.presenter;

import android.content.ContentValues;
import android.content.Context;

import com.medialink.submission4.Const;
import com.medialink.submission4.FavoriteMovieContract;
import com.medialink.submission4.FavoriteTvContract;
import com.medialink.submission4.database.DatabaseContract;
import com.medialink.submission4.database.FavoriteHelper;
import com.medialink.submission4.model.FavoriteItem;
import com.medialink.submission4.model.movie.MovieItem;

public class FavoritePresenter
        implements FavoriteMovieContract.PresenterInterface {

    private FavoriteMovieContract.ViewInterface mViewInterface;
    private FavoriteTvContract.ViewInterface mViewTvInface;
    private Context context;

    public FavoritePresenter(FavoriteMovieContract.ViewInterface mViewInterface, Context context) {
        this.mViewInterface = mViewInterface;
        this.context = context;
    }

    public FavoritePresenter(FavoriteTvContract.ViewInterface mViewTvIntface, Context context) {
        this.mViewTvInface = mViewTvIntface;
        this.context = context;
    }

    @Override
    public void getMovies(int page) {

    }

    @Override
    public void clickFavorite(boolean isEdit, MovieItem movie, String id) {
        FavoriteItem fav = new FavoriteItem();
        FavoriteHelper favHelper = FavoriteHelper.getInstance(context);

        fav.setMovie_id(movie.getId());
        fav.setType_id(Const.MOVIE_TYPE);
        fav.setOverview(movie.getOverview());
        fav.setPoster_path(movie.getPosterPath());
        fav.setRelease_date(movie.getReleaseDate());
        fav.setTitle(movie.getTitle());

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.tableFavorite.TITLE, movie.getTitle());
        values.put(DatabaseContract.tableFavorite.RELEASE_DATE, movie.getReleaseDate());
        values.put(DatabaseContract.tableFavorite.MOVIE_ID, movie.getId());
        values.put(DatabaseContract.tableFavorite.TYPE_ID, Const.MOVIE_TYPE);
        values.put(DatabaseContract.tableFavorite.OVERVIEW, movie.getOverview());
        values.put(DatabaseContract.tableFavorite.POSTER_PATH, movie.getPosterPath());

        if (isEdit) {
            long result = favHelper.update(id, values);
            //setResult(RESULT_UPDATE, intent);
            //finish();
            //Toast.makeText(NoteAddUpdateActivity.this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
        } else {
            long result = favHelper.insert(values);
            if (result > 0) {
                fav.setId((int) result);
                //setResult(RESULT_ADD, intent);
                //finish();
            }  //Toast.makeText(NoteAddUpdateActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();

        }

        favHelper.close();
    }

    @Override
    public void itemDelete(String id, int position) {
        FavoriteHelper favHelper = FavoriteHelper.getInstance(context);
        favHelper.open();

        long result = favHelper.deleteById(id);
        if (result > 0) {
            if (mViewTvInface != null) {
                mViewTvInface.refreshRemoveItem(position);
            } else {
                mViewInterface.refreshRemoveItem(position);
            }

        } else {
            if (mViewTvInface != null) {
                mViewTvInface.setError("Gagal Hapus Favorite");
            } else {
                mViewInterface.setError("Gagal Hapus Favorite");
            }

        }

        favHelper.close();
    }
}
