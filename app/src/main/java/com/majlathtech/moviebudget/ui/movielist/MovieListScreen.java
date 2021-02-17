package com.majlathtech.moviebudget.ui.movielist;

import com.majlathtech.moviebudget.network.model.MovieDetail;

import java.util.List;

public interface MovieListScreen {
    void showMovies(List<MovieDetail> movieList);
    void showError(String errorMsg);
}
