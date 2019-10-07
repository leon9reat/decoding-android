package com.medialink.submission4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.medialink.submission4.model.FavoriteItem;

import static android.provider.BaseColumns._ID;

public class FavoriteHelper {
    private static final String TABLE = DatabaseContract.tableFavorite.TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavoriteHelper() {
    }

    public FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }

        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor queryAll(int typeId) {
        return database.query(TABLE,
                null,
                DatabaseContract.tableFavorite.TYPE_ID + " = ?",
                new String[]{String.valueOf(typeId)},
                null,
                null,
                _ID + " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(TABLE, values, _ID + " = ?",
                new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(TABLE, _ID + " = ?",
                new String[]{id});
    }

    public FavoriteItem getFav(int id, int typeId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE,
                null,
                DatabaseContract.tableFavorite.MOVIE_ID + " = ? AND "
                        +DatabaseContract.tableFavorite.TYPE_ID + " = ?",
                new String[]{String.valueOf(id), String.valueOf(typeId)},
                null,
                null,
                null);

        FavoriteItem fav = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            fav = new FavoriteItem(
                    cursor.getInt(cursor.getColumnIndex(_ID)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.tableFavorite.MOVIE_ID)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.tableFavorite.TYPE_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.tableFavorite.POSTER_PATH)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.tableFavorite.OVERVIEW)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.tableFavorite.RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.tableFavorite.TITLE)));
            cursor.close();
        }

        return fav;
    }
}
