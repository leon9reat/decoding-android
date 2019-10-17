package com.medialink.submission5.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.medialink.submission5.BuildConfig;
import com.medialink.submission5.Const;
import com.medialink.submission5.R;
import com.medialink.submission5.model.local.AppDatabase;
import com.medialink.submission5.model.local.FavoriteDao;
import com.medialink.submission5.model.local.FavoriteItem;

import java.util.ArrayList;
import java.util.List;

public class MovieRemoteViewFactory
        implements RemoteViewsService.RemoteViewsFactory{

    private static final String TAG = "MovieRemoteViewFactory";
    private final Context mContext;
    private List<FavoriteItem> listFavorite = new ArrayList<>();
    private AppDatabase database;
    private FavoriteDao favDao;

    public MovieRemoteViewFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        database = AppDatabase.getInstance(mContext);
        favDao = database.getFavoriteDao();

        listFavorite.clear();
        //listFavorite.addAll(favDao.getFavoriteByType(Const.DETAIL_MOVIE));
        listFavorite.addAll(favDao.getAllFavorite());

       // new LoadAsync(mContext, this).execute();
        /*Cursor dataCursor = mContext.getContentResolver().query(
                Uri.parse(FavoriteContentProvider.URI_FAVORITE + "/" + Const.DETAIL_MOVIE),
                null,
                null,
                null,
                null);

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

                listFavorite.add(provFavItem);
                Log.d(TAG, "doInBackground: "+provFavItem.getTitle());
            }
        }

         */


    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listFavorite.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.ImageUrl+listFavorite.get(position).getPosterPath())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.img_poster_widget, bitmap);
            Log.d(TAG, "getViewAt: "+listFavorite.get(position).getPosterPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        rv.setTextViewText(R.id.tv_title_widget, listFavorite.get(position).getTitle());

        Bundle extras = new Bundle();
        extras.putInt(MovieWidget.EXTRA_ITEM, position);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.img_poster_widget, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}



