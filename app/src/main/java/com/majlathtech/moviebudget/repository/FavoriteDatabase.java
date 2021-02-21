package com.majlathtech.moviebudget.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMovieElement.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavoriteDao favDao();
}

