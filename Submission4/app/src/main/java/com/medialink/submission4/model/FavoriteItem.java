package com.medialink.submission4.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteItem implements Parcelable {
    private int id;
    private int movie_id;
    private int type_id; // 1 movie; 2 tv
    private String poster_path;
    private String overview;
    private String release_date;
    private String title;

    public FavoriteItem() {
    }

    public FavoriteItem(int id, int movie_id, int type_id, String poster_path, String overview,
                        String release_date, String title) {
        this.id = id;
        this.movie_id = movie_id;
        this.type_id = type_id;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.movie_id);
        dest.writeInt(this.type_id);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.title);
    }

    protected FavoriteItem(Parcel in) {
        this.id = in.readInt();
        this.movie_id = in.readInt();
        this.type_id = in.readInt();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.title = in.readString();
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
