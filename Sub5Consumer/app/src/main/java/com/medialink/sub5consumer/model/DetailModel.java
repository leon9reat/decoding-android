package com.medialink.sub5consumer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailModel implements Parcelable {
    private String title;
    private String releaseDate;
    private String labelReleaseDate;
    private String overview;
    private String voteAverage;
    private String posterPath;
    private String labelRuntime;
    private int runtime;
    private int hour_or_season;
    private int min_or_episode;
    private String status;
    private String genre;
    private String castName1;
    private String profilePath1;
    private String castName2;
    private String profilePath2;
    private String castName3;
    private String profilePath3;
    private String castName4;
    private String profilePath4;
    private String castName5;
    private String profilePath5;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHour_or_season() {
        return hour_or_season;
    }

    public void setHour_or_season(int hour_or_season) {
        this.hour_or_season = hour_or_season;
    }

    public int getMin_or_episode() {
        return min_or_episode;
    }

    public void setMin_or_episode(int min_or_episode) {
        this.min_or_episode = min_or_episode;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLabelReleaseDate() {
        return labelReleaseDate;
    }

    public void setLabelReleaseDate(String labelReleaseDate) {
        this.labelReleaseDate = labelReleaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getLabelRuntime() {
        return labelRuntime;
    }

    public void setLabelRuntime(String labelRuntime) {
        this.labelRuntime = labelRuntime;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCastName1() {
        return castName1;
    }

    public void setCastName1(String castName1) {
        this.castName1 = castName1;
    }

    public String getProfilePath1() {
        return profilePath1;
    }

    public void setProfilePath1(String profilePath1) {
        this.profilePath1 = profilePath1;
    }

    public String getCastName2() {
        return castName2;
    }

    public void setCastName2(String castName2) {
        this.castName2 = castName2;
    }

    public String getProfilePath2() {
        return profilePath2;
    }

    public void setProfilePath2(String profilePath2) {
        this.profilePath2 = profilePath2;
    }

    public String getCastName3() {
        return castName3;
    }

    public void setCastName3(String castName3) {
        this.castName3 = castName3;
    }

    public String getProfilePath3() {
        return profilePath3;
    }

    public void setProfilePath3(String profilePath3) {
        this.profilePath3 = profilePath3;
    }

    public String getCastName4() {
        return castName4;
    }

    public void setCastName4(String castName4) {
        this.castName4 = castName4;
    }

    public String getProfilePath4() {
        return profilePath4;
    }

    public void setProfilePath4(String profilePath4) {
        this.profilePath4 = profilePath4;
    }

    public String getCastName5() {
        return castName5;
    }

    public void setCastName5(String castName5) {
        this.castName5 = castName5;
    }

    public String getProfilePath5() {
        return profilePath5;
    }

    public void setProfilePath5(String profilePath5) {
        this.profilePath5 = profilePath5;
    }


    public DetailModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.releaseDate);
        dest.writeString(this.labelReleaseDate);
        dest.writeString(this.overview);
        dest.writeString(this.voteAverage);
        dest.writeString(this.posterPath);
        dest.writeString(this.labelRuntime);
        dest.writeInt(this.runtime);
        dest.writeInt(this.hour_or_season);
        dest.writeInt(this.min_or_episode);
        dest.writeString(this.status);
        dest.writeString(this.genre);
        dest.writeString(this.castName1);
        dest.writeString(this.profilePath1);
        dest.writeString(this.castName2);
        dest.writeString(this.profilePath2);
        dest.writeString(this.castName3);
        dest.writeString(this.profilePath3);
        dest.writeString(this.castName4);
        dest.writeString(this.profilePath4);
        dest.writeString(this.castName5);
        dest.writeString(this.profilePath5);
    }

    protected DetailModel(Parcel in) {
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.labelReleaseDate = in.readString();
        this.overview = in.readString();
        this.voteAverage = in.readString();
        this.posterPath = in.readString();
        this.labelRuntime = in.readString();
        this.runtime = in.readInt();
        this.hour_or_season = in.readInt();
        this.min_or_episode = in.readInt();
        this.status = in.readString();
        this.genre = in.readString();
        this.castName1 = in.readString();
        this.profilePath1 = in.readString();
        this.castName2 = in.readString();
        this.profilePath2 = in.readString();
        this.castName3 = in.readString();
        this.profilePath3 = in.readString();
        this.castName4 = in.readString();
        this.profilePath4 = in.readString();
        this.castName5 = in.readString();
        this.profilePath5 = in.readString();
    }

    public static final Creator<DetailModel> CREATOR = new Creator<DetailModel>() {
        @Override
        public DetailModel createFromParcel(Parcel source) {
            return new DetailModel(source);
        }

        @Override
        public DetailModel[] newArray(int size) {
            return new DetailModel[size];
        }
    };
}
