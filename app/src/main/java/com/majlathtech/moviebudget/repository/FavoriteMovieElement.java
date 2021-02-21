package com.majlathtech.moviebudget.repository;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteMovieElement {
    @PrimaryKey
    public int id;
    public int budget;
    @ColumnInfo(name = "poster_path")
    public String posterPath;
    @ColumnInfo(name = "backdrop_path")
    public String backdropPath;
    public String title;
}
