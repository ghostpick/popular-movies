package com.popularmovies.ghostpick.popularmovies.data;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String overview;
    private String photo;
    private String releaseDate;
    private String voteAverage;

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

}
