package com.majlathtech.moviebudget.ui.movielist;

import com.majlathtech.moviebudget.network.model.MovieDetail;

import java.util.List;

public interface MovieSearchScreen {
    void showFavorites(List<MovieDetail> favoriteMovieElements);
    void showMovies(List<MovieDetail> movieList);
    void showError(String errorMsg);
    void showNetworkError();
}
