package com.majlathtech.moviebudget.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.majlathtech.moviebudget.network.model.MovieDetail;

@Database(entities = {MovieDetail.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavoriteDao favDao();
}

