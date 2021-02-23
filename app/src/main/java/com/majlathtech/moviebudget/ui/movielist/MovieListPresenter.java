package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;

import androidx.annotation.NonNull;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.service.MovieService;
import com.majlathtech.moviebudget.repository.FavoriteDao;
import com.majlathtech.moviebudget.repository.FavoriteDatabase;
import com.majlathtech.moviebudget.ui.RxPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

public class MovieListPresenter extends RxPresenter<MovieListScreen> {
    private static final String TAG = MovieListPresenter.class.getSimpleName();

    private final Context context;
    private final MovieService movieService;
    private final FavoriteDao favoriteDao;

    @Inject
    public MovieListPresenter(Context context, MovieService movieService, FavoriteDatabase favoriteDatabase) {
        this.context = context;
        this.movieService = movieService;
        this.favoriteDao = favoriteDatabase.favDao();
    }

    public void addToFavorites(@NonNull MovieDetail... favoriteMovieElements) {
        performTask(favoriteDao.insertAll(favoriteMovieElements));
    }

    public void removeFromFavorites(@NonNull MovieDetail favoriteMovieElement) {
        performTask(favoriteDao.delete(favoriteMovieElement));
    }

    public void getFavorites() {
        performTask(favoriteDao.getAll(), screen::showFavorites, Throwable::printStackTrace);
    }

    public void searchMovie(final String movieTitle) {
        if (movieTitle == null || movieTitle.isEmpty()) {
            screen.showMovies(new ArrayList<>());
            return;
        }
        performTask(movieService.searchMovie(movieTitle),
                movieDetails -> screen.showMovies(movieDetails),
                throwable -> {
                    throwable.printStackTrace();
                    screen.showError(context.getString(R.string.unexpected_error_happened));
                });
    }
}
