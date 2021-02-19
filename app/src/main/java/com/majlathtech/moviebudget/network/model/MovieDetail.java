package com.majlathtech.moviebudget.network.model;

import com.google.gson.annotations.SerializedName;
import com.majlathtech.moviebudget.ui.composite.model.Listable;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDetail that = (MovieDetail) o;
        return id == that.id &&
                budget == that.budget &&
                Objects.equals(posterPath, that.posterPath) &&
                Objects.equals(backdropPath, that.backdropPath) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, budget, posterPath, backdropPath, title);
    }
}
