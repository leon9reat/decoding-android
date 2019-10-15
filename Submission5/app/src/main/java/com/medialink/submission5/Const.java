package com.medialink.submission5;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class Const {
    public static final int DETAIL_MOVIE = 1;
    public static final int DETAIL_TV = 2;
    public static final int CHANGE_LANGUAGE_REQUEST = 10;
    public static final int ID_NOTIF_RELEASE = 20;
    public static final int ID_NOTIF_DAILY = 21;
    public static final String KEY_DETAIL_TYPE = "DETAIL_TYPE";
    public static final String KEY_ID = "ID";
    public static final String ALARM_ONE_TIME = "OneTimeAlarm";
    public static final String ALARM_REPEATING = "RepeatingAlarm";
    public static final int REQUEST_ONE_TIME = 100;
    public static final int REQUEST_REPEAT = 101;
    public static final String ALARM_RELEASE_TIME = "01:50";
    public static final String ALARM_REMINDER_TIME = "00:44";

    public static final String DATABASE_NAME = "movie_db";
    public static final String TABLE_FAVORITE = "favorite";
    public static final String FIELD_FAVORITE_ID = "id";
    public static final String FIELD_FAVORITE_MOVIE_ID = "movieId";
    public static final String FIELD_FAVORITE_TYPE_ID = "typeId"; // 1 movie; 2 tv
    public static final String FIELD_FAVORITE_POSTER_PATH = "posterPath";
    public static final String FIELD_FAVORITE_TITLE = "title";
    public static final String FIELD_FAVORITE_RELEASE_DATE = "releaseDate";
    public static final String FIELD_FAVORITE_OVERVIEW = "overview";

}
