package com.medialink.submission4.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final class tableFavorite implements BaseColumns {
        public static String TABLE_NAME = "favorite";
        public static String MOVIE_ID = "movie_id";
        public static String TYPE_ID = "type_id"; // 1 movie; 2 tv
        public static String POSTER_PATH = "poster_path";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date";
        public static String TITLE = "title";
    }
}
