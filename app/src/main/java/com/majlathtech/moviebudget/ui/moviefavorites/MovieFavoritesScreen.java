package com.majlathtech.moviebudget.ui.moviefavorites;

import com.majlathtech.moviebudget.network.model.MovieDetail;

import java.util.List;

public interface MovieFavoritesScreen {
    void showFavorites(List<MovieDetail> favoriteMovieElements);
    void showError(String errorMsg);
}
