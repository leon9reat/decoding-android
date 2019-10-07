package com.medialink.submission4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbfavorite";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_FAVORITE =
            String.format("CREATE TABLE %s"+
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    " %s INTEGER NOT NULL,"+
                    " %s INTEGER,"+
                    " %s TEXT,"+
                    " %s TEXT,"+
                    " %s TEXT,"+
                    " %s TEXT)",
                    DatabaseContract.tableFavorite.TABLE_NAME,
                    DatabaseContract.tableFavorite._ID,
                    DatabaseContract.tableFavorite.MOVIE_ID,
                    DatabaseContract.tableFavorite.TYPE_ID,
                    DatabaseContract.tableFavorite.POSTER_PATH,
                    DatabaseContract.tableFavorite.OVERVIEW,
                    DatabaseContract.tableFavorite.RELEASE_DATE,
                    DatabaseContract.tableFavorite.TITLE);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.tableFavorite.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
