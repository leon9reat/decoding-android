package com.medialink.sub5consumer;

import android.net.Uri;

public class Const {
    public static final int DETAIL_MOVIE = 1;
    public static final int DETAIL_TV = 2;
    public static final String KEY_DETAIL_TYPE = "DETAIL_TYPE";
    public static final String KEY_ID = "ID";

    public static final String TABLE_FAVORITE = "favorite";
    public static final String FIELD_FAVORITE_ID = "id";
    public static final String FIELD_FAVORITE_MOVIE_ID = "movieId";
    public static final String FIELD_FAVORITE_TYPE_ID = "typeId"; // 1 movie; 2 tv
    public static final String FIELD_FAVORITE_POSTER_PATH = "posterPath";
    public static final String FIELD_FAVORITE_TITLE = "title";
    public static final String FIELD_FAVORITE_RELEASE_DATE = "releaseDate";
    public static final String FIELD_FAVORITE_OVERVIEW = "overview";

    public static final String AUTHORITY = "com.medialink.submission5.model.provider";
    public static final String SCHEME = "content";
    //public static final Uri URI_FAVORITE = Uri.parse("content://" + AUTHORITY + "/" + Const.TABLE_FAVORITE);
    public static final Uri URI_FAVORITE = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(Const.TABLE_FAVORITE)
            .build();

}
