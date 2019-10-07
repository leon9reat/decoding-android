package com.medialink.submission4.helper;

import android.database.Cursor;

import com.medialink.submission4.database.DatabaseContract;
import com.medialink.submission4.model.FavoriteItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

public class MappingHelper {
    public static ArrayList<FavoriteItem> mapCursorToArrayList(Cursor favCursor) {
        ArrayList<FavoriteItem> favList = new ArrayList<>();
        while (favCursor.moveToNext()) {
            int id = favCursor.getInt(favCursor.getColumnIndex(_ID));
            int movie_id = favCursor.getInt(favCursor.getColumnIndex(DatabaseContract.tableFavorite.MOVIE_ID));
            int type_id = favCursor.getInt(favCursor.getColumnIndex(DatabaseContract.tableFavorite.TYPE_ID));
            String poster_path = favCursor.getString(favCursor.getColumnIndexOrThrow(DatabaseContract.tableFavorite.POSTER_PATH));
            String overview = favCursor.getString(favCursor.getColumnIndexOrThrow(DatabaseContract.tableFavorite.OVERVIEW));
            String release_date = favCursor.getString(favCursor.getColumnIndexOrThrow(DatabaseContract.tableFavorite.RELEASE_DATE));
            String title = favCursor.getString(favCursor.getColumnIndexOrThrow(DatabaseContract.tableFavorite.TITLE));

            favList.add(new FavoriteItem(id, movie_id, type_id, poster_path, overview, release_date, title));
        }

        return favList;
    }
}
