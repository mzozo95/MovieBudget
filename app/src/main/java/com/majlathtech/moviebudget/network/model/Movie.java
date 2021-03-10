package com.majlathtech.moviebudget.network.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("originalTitle")
    private String originalTitle;
    private String title;
    @SerializedName("voteAverage")
    private double voteAverage;

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
