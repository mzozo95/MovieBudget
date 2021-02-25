package com.majlathtech.moviebudget.network.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity
public class MovieDetail {
    @PrimaryKey
    public int id;
    public int budget;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    public String posterPath;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    public String backdropPath;
    public String title;

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
