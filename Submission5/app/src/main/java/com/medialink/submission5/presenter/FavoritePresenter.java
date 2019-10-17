package com.medialink.submission5.presenter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.medialink.submission5.Const;
import com.medialink.submission5.contract.FavoriteContract;
import com.medialink.submission5.model.local.AppDatabase;
import com.medialink.submission5.model.local.FavoriteDao;
import com.medialink.submission5.model.local.FavoriteItem;
import com.medialink.submission5.model.provider.FavoriteContentProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
        database = Room.databaseBuilder(context, AppDatabase.class, Const.DATABASE_NAME)
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
    public void getMovieProvider(Context context) {
        mFavMovie.showLoading(true);
        new LoadFavoriteAsyn(context, mFavMovie).execute(Const.DETAIL_MOVIE);
    }

    @Override
    public void deleteMovie(FavoriteItem item) {
        mFavMovie.showLoading(true);
        favDao.delete(item);
        mFavMovie.showLoading(false);
    }


    @Override
    public void getTv() {
        mFavTv.showLoading(true);
        List<FavoriteItem> items = favDao.getFavoriteByType(Const.DETAIL_TV);
        mFavTv.showTv(items);
    }

    @Override
    public void getTvProvider(Context context) {
        mFavTv.showLoading(true);
        new LoadFavoriteAsyn(context, mFavTv).execute(Const.DETAIL_TV);
    }

    @Override
    public void deleteTv(FavoriteItem item) {
        mFavTv.showLoading(true);
        favDao.delete(item);
        mFavTv.showLoading(false);
    }

    @Override
    public void refreshWidget() {
        mFavView.refreshWidget();
    }

    private static class LoadFavoriteAsyn extends AsyncTask<Integer, Void, ArrayList<FavoriteItem>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<FavoriteContract.MovieFavInterface> weakCallback;
        private final WeakReference<FavoriteContract.TvFavInterface> weakTvCallback;

        public LoadFavoriteAsyn(Context context, FavoriteContract.MovieFavInterface movieInterface) {
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(movieInterface);
            this.weakTvCallback = null;
        }

        public LoadFavoriteAsyn(Context context, FavoriteContract.TvFavInterface tvFavInterface) {
            this.weakContext = new WeakReference<>(context);
            this.weakTvCallback = new WeakReference<>(tvFavInterface);
            this.weakCallback = null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: get content provider list ");
        }

        @Override
        protected ArrayList<FavoriteItem> doInBackground(Integer... params) {
            Context context = weakContext.get();

            String uri = String.format("%s/%d",FavoriteContentProvider.URI_FAVORITE, params[0].intValue());
            Cursor dataCursor = context.getContentResolver().query(
                    Uri.parse(uri),
                    null,
                    null,
                    null,
                    null);

            ArrayList<FavoriteItem> provFavItems = new ArrayList<>();

            if (dataCursor != null) {
                while (dataCursor.moveToNext()) {
                    final FavoriteItem provFavItem = new FavoriteItem();
                    provFavItem.setId(dataCursor.getLong(dataCursor.getColumnIndexOrThrow(Const.FIELD_FAVORITE_ID)));
                    provFavItem.setMovieId(dataCursor.getInt(dataCursor.getColumnIndexOrThrow(Const.FIELD_FAVORITE_MOVIE_ID)));
                    provFavItem.setTypeId(dataCursor.getInt(dataCursor.getColumnIndexOrThrow(Const.FIELD_FAVORITE_TYPE_ID)));
                    provFavItem.setPosterPath(dataCursor.getString(dataCursor.getColumnIndexOrThrow(Const.FIELD_FAVORITE_POSTER_PATH)));
                    provFavItem.setTitle(dataCursor.getString(dataCursor.getColumnIndexOrThrow(Const.FIELD_FAVORITE_TITLE)));
                    provFavItem.setReleaseDate(dataCursor.getString(dataCursor.getColumnIndexOrThrow(Const.FIELD_FAVORITE_RELEASE_DATE)));
                    provFavItem.setOverview(dataCursor.getString(dataCursor.getColumnIndexOrThrow(Const.FIELD_FAVORITE_OVERVIEW)));

                    provFavItems.add(provFavItem);
                }
            }
            return provFavItems;
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteItem> provFavItems) {
            super.onPostExecute(provFavItems);
            if (weakCallback != null) {
                weakCallback.get().showLoading(false);
                weakCallback.get().showMovie(provFavItems);
            } else {
                weakTvCallback.get().showLoading(false);
                weakTvCallback.get().showTv(provFavItems);
            }

            Log.d(TAG, "onPostExecute: " + provFavItems.size());
        }
    }
}


