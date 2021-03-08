package com.majlathtech.moviebudget.network.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity
public class MovieDetail {
    @PrimaryKey
    public final int id;
    public final int budget;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    public final String posterPath;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    public final String backdropPath;
    public final String title;

    public MovieDetail(int id, int budget, String posterPath, String backdropPath, String title) {
        this.id = id;
        this.budget = budget;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.title = title;
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
