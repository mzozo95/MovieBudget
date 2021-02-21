package com.majlathtech.moviebudget.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface  FavoriteDao {/*
https://developer.android.com/training/data-storage/room
    @Query("SELECT * FROM user")
    List<FavoriteMovieElement> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);*/
}
