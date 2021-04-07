package com.majlathtech.moviebudget.repository;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    public RepositoryModule() {
    }

    @Provides
    @Singleton
    public FavoriteDatabase provideFavoriteDb(Context context) {
        return Room.databaseBuilder(context, FavoriteDatabase.class, FavoriteDatabase.class.getSimpleName()).build();
    }

    @Provides
    @Singleton
    public FavoriteDao provideFavoriteDao(FavoriteDatabase database) {
        return database.favDao();
    }
}
