package com.popularmovies.ghostpick.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String overview;
    private String photo;
    private String releaseDate;
    private String voteAverage;

    public Movie() {}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getOverview() {return overview;}
    public void setOverview(String overview) {this.overview = overview;}

    public String getPhoto() {return photo;}
    public void setPhoto(String photo) {this.photo = photo;}

    public String getReleaseDate() {return releaseDate;}
    public void setReleaseDate(String releaseDate) {this.releaseDate = releaseDate;}

    public String getVoteAverage() {return voteAverage;}
    public void setVoteAverage(String voteAverage) {this.voteAverage = voteAverage;}

    //////////////////////////////////////////////////////////////////////////////////// Parcelable

    public static final String PARCELABLE_KEY = "movie";

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public Movie(Parcel in) {
        title = in.readString();
        overview = in.readString();
        photo = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(photo);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}