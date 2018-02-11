package com.majlathtech.moviebudget.ui.movielist;

import com.majlathtech.moviebudget.network.model.Movie;

import java.util.List;

public interface MovieListScreen {
    void showMovies(List<Movie> movieList);
    void showError(String errorMsg);
}
