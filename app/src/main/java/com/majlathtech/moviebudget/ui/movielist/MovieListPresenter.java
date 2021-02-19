package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.service.MovieService;
import com.majlathtech.moviebudget.ui.RxPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

public class MovieListPresenter extends RxPresenter<MovieListScreen> {
    private static final String TAG = MovieListPresenter.class.getSimpleName();

    private final Context context;
    private final MovieService movieService;

    @Inject
    public MovieListPresenter(Context context, MovieService movieService) {
        this.context = context;
        this.movieService = movieService;
    }

    public void searchMovie(final String movieTitle) {
        if (movieTitle == null || movieTitle.isEmpty()) {
            screen.showMovies(new ArrayList<>());
            return;
        }
        performRequest(movieService.searchMovie(movieTitle),
                movieDetails -> screen.showMovies(movieDetails),
                throwable -> {
                    throwable.printStackTrace();
                    screen.showError(context.getString(R.string.unexpected_error_happened));
                });
    }
}
