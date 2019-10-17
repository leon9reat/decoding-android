package com.medialink.submission5.model.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.medialink.submission5.Const;
import com.medialink.submission5.model.local.FavoriteItem;

public class FavoriteContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.medialink.submission5.model.provider";
    public static final String SCHEME = "content";
    //public static final Uri URI_FAVORITE = Uri.parse("content://" + AUTHORITY + "/" + Const.TABLE_FAVORITE);
    public static final Uri URI_FAVORITE = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(Const.TABLE_FAVORITE)
            .build();

    private static final int CODE_FAV_DIR = 1;
    private static final int CODE_FAV_TYPE = 2;
    private static final int CODE_FAV_ITEM_MOVIE = 3;
    private static final int CODE_FAV_ITEM_TV = 4;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, Const.TABLE_FAVORITE, CODE_FAV_DIR);
        MATCHER.addURI(AUTHORITY, Const.TABLE_FAVORITE + "/*", CODE_FAV_TYPE);
        MATCHER.addURI(AUTHORITY, Const.TABLE_FAVORITE + "/movie/*", CODE_FAV_ITEM_MOVIE);
        MATCHER.addURI(AUTHORITY, Const.TABLE_FAVORITE + "/tv/*", CODE_FAV_ITEM_TV);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final int code = MATCHER.match(uri);
        if (code == CODE_FAV_DIR || code == CODE_FAV_ITEM_MOVIE ||
                code == CODE_FAV_ITEM_TV || code == CODE_FAV_TYPE) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }

            ProvFavDao provFavDao = ProvAppDatabase.getInstance(context).getProvFavDao();
            Cursor cursor;
            switch (code) {
                case CODE_FAV_DIR:
                    cursor = provFavDao.getAllFavorite();
                    break;
                case CODE_FAV_TYPE:
                    Log.d("test", "query: "+uri.toString());
                    cursor = provFavDao.getFavoriteByType(Integer.valueOf(uri.getLastPathSegment()));
                    break;
                case CODE_FAV_ITEM_MOVIE:
                    cursor = provFavDao.getFavoriteByMovieId(
                            Const.DETAIL_MOVIE,
                            Integer.valueOf(uri.getLastPathSegment()));
                    break;
                case CODE_FAV_ITEM_TV:
                    cursor = provFavDao.getFavoriteByMovieId(
                            Const.DETAIL_TV,
                            Integer.valueOf(uri.getLastPathSegment()));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + code);
            }

            //cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_FAV_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Const.TABLE_FAVORITE;
            case CODE_FAV_TYPE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Const.TABLE_FAVORITE;
            case CODE_FAV_ITEM_MOVIE:
                return "vnd.android.cursor.item/movie" + AUTHORITY + "." + Const.TABLE_FAVORITE;
            case CODE_FAV_ITEM_TV:
                return "vnd.android.cursor.item/tv" + AUTHORITY + "." + Const.TABLE_FAVORITE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (MATCHER.match(uri)) {
            case CODE_FAV_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final Long id = ProvAppDatabase.getInstance(context).getProvFavDao()
                        .insert(FavoriteItem.fromContentValue(contentValues));

                Uri resultUri = Uri.parse(String.valueOf(uri));
                if (contentValues.containsKey(Const.FIELD_FAVORITE_MOVIE_ID)) {
                    int movieId = contentValues.getAsInteger(Const.FIELD_FAVORITE_MOVIE_ID);
                    int typeId = contentValues.getAsInteger(Const.FIELD_FAVORITE_TYPE_ID);
                    if (typeId == Const.DETAIL_MOVIE) {
                        resultUri = Uri.parse(uri + "/movie/" + movieId);
                    } else {
                        resultUri = Uri.parse(uri + "/tv/"+ movieId);
                    }
                }

                context.getContentResolver().notifyChange(uri, null);
                return resultUri;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        switch (MATCHER.match(uri)) {
            case CODE_FAV_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_FAV_TYPE:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = ProvAppDatabase.getInstance(context).getProvFavDao()
                        .deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        switch (MATCHER.match(uri)) {
            case CODE_FAV_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_FAV_TYPE:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final FavoriteItem provFavItem = FavoriteItem.fromContentValue(contentValues);
                provFavItem.setId(ContentUris.parseId(uri));
                final int count = ProvAppDatabase.getInstance(context).getProvFavDao()
                        .update(provFavItem);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
