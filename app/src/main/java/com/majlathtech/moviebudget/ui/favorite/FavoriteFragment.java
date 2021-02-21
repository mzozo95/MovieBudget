package com.majlathtech.moviebudget.ui.favorite;

import com.majlathtech.moviebudget.repository.FavoriteDatabase;

import javax.inject.Inject;

public class FavoriteFragment {
    FavoriteDatabase favoriteDatabase;

    @Inject
    public FavoriteFragment(FavoriteDatabase favoriteDatabase){
        this.favoriteDatabase = favoriteDatabase;//Todo
    }
}
