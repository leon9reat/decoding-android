package com.medialink.sub5consumer.model.local;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.medialink.sub5consumer.Const;

public class FavoriteItem implements Parcelable {
    private Long id;
    private int movieId;
    private int typeId; // 1 movie; 2 tv
    private String posterPath;
    private String title;
    private String releaseDate;
    private String overview;

    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public static FavoriteItem fromContentValue(ContentValues values) {
        final FavoriteItem provFavItem = new FavoriteItem();

        if (values.containsKey(Const.FIELD_FAVORITE_ID)) {
            provFavItem.id = values.getAsLong(Const.FIELD_FAVORITE_ID);
        }
        if (values.containsKey(Const.FIELD_FAVORITE_MOVIE_ID)) {
            provFavItem.movieId = values.getAsInteger(Const.FIELD_FAVORITE_MOVIE_ID);
        }
        if (values.containsKey(Const.FIELD_FAVORITE_TYPE_ID)) {
            provFavItem.typeId = values.getAsInteger(Const.FIELD_FAVORITE_TYPE_ID);
        }
        if (values.containsKey(Const.FIELD_FAVORITE_POSTER_PATH)) {
            provFavItem.posterPath = values.getAsString(Const.FIELD_FAVORITE_POSTER_PATH);
        }
        if (values.containsKey(Const.FIELD_FAVORITE_TITLE)) {
            provFavItem.title = values.getAsString(Const.FIELD_FAVORITE_TITLE);
        }
        if (values.containsKey(Const.FIELD_FAVORITE_RELEASE_DATE)) {
            provFavItem.releaseDate = values.getAsString(Const.FIELD_FAVORITE_RELEASE_DATE);
        }
        if (values.containsKey(Const.FIELD_FAVORITE_OVERVIEW)) {
            provFavItem.overview = values.getAsString(Const.FIELD_FAVORITE_OVERVIEW);
        }

        return provFavItem;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeInt(this.movieId);
        dest.writeInt(this.typeId);
        dest.writeString(this.posterPath);
        dest.writeString(this.title);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
    }

    public FavoriteItem() {
    }

    protected FavoriteItem(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.movieId = in.readInt();
        this.typeId = in.readInt();
        this.posterPath = in.readString();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<FavoriteItem> CREATOR = new Parcelable.Creator<FavoriteItem>() {
        @Override
        public FavoriteItem createFromParcel(Parcel source) {
            return new FavoriteItem(source);
        }

        @Override
        public FavoriteItem[] newArray(int size) {
            return new FavoriteItem[size];
        }
    };
}
