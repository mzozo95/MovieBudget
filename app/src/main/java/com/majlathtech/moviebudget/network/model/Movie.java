package com.majlathtech.moviebudget.network.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    public final int id;
    @SerializedName("poster_path")
    public final String posterPath;
    @SerializedName("backdrop_path")
    public final String backdropPath;
    public final String overview;
    @SerializedName("release_date")
    public final String releaseDate;
    @SerializedName("originalTitle")
    public final String originalTitle;
    public final String title;
    @SerializedName("voteAverage")
    public final double voteAverage;

    public Movie(int id, String posterPath, String backdropPath, String overview, String releaseDate, String originalTitle, String title, double voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.title = title;
        this.voteAverage = voteAverage;
    }
}
