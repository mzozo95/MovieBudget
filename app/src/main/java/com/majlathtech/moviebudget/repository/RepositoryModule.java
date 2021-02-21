package com.majlathtech.moviebudget.repository;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    private final Context context;

    public RepositoryModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public FavoriteDatabase provideFavoriteDb() {
        return Room.databaseBuilder(context, FavoriteDatabase.class, FavoriteDatabase.class.getSimpleName()).build();
    }
}
