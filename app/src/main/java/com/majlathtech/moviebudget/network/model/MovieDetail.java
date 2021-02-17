package com.majlathtech.moviebudget.network.model;

import com.google.gson.annotations.SerializedName;
import com.majlathtech.moviebudget.ui.composite.model.Listable;

public class MovieDetail implements Listable {
    private int id;
    private int budget;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private String title;

    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getTitle() {
        return title;
    }
}
