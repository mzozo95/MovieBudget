package com.majlathtech.moviebudget.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    public final int page;
    public final List<Movie> results;
    @SerializedName("total_results")
    public final int totalResults;
    @SerializedName("total_pages")
    public final int totalPages;

    public MovieResponse(int page, List<Movie> results, int totalResults, int totalPages) {
        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }
}
