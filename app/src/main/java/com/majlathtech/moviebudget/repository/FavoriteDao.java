package com.majlathtech.moviebudget.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.majlathtech.moviebudget.network.model.MovieDetail;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface  FavoriteDao {
    @Query("SELECT * FROM movieDetail ORDER BY title")
    Single<List<MovieDetail>> getAll();

    @Insert
    Completable insertAll(MovieDetail... favoriteMovieElements);

    @Delete
    Completable delete(MovieDetail favoriteMovieElement);
}
