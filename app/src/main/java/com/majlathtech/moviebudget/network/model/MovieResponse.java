package com.majlathtech.moviebudget.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    private int page;
    private List<Movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_results")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
