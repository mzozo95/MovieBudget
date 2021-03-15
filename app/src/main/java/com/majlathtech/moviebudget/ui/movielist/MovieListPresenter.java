package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.service.MovieService;
import com.majlathtech.moviebudget.network.service.MovieServiceErrorCodes;
import com.majlathtech.moviebudget.repository.FavoriteDao;
import com.majlathtech.moviebudget.repository.FavoriteDatabaseErrorCodes;
import com.majlathtech.moviebudget.ui.base.RxPresenter;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

public class MovieListPresenter extends RxPresenter<MovieListScreen> {
    private final Context context;
    private final MovieService movieService;
    private final FavoriteDao favoriteDao;

    @Inject
    public MovieListPresenter(Context context, MovieService movieService, FavoriteDao favoriteDao) {
        this.context = context;
        this.movieService = movieService;
        this.favoriteDao = favoriteDao;
    }

    public void addToFavorites(@NonNull MovieDetail... favoriteMovieElements) {
        performTask(favoriteDao.insertAll(favoriteMovieElements), throwable -> showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_INSERT_ITEM, throwable));
    }

    public void removeFromFavorites(@NonNull MovieDetail favoriteMovieElement) {
        performTask(favoriteDao.delete(favoriteMovieElement), throwable -> showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_REMOVE_ITEM, throwable));
    }

    public void getFavorites() {
        performTask(favoriteDao.getAll(), screen::showFavorites, throwable -> showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_NOT_GET_ALL, throwable));
    }

    public void searchMovie(final String movieTitle) {
        if (movieTitle == null || movieTitle.isEmpty()) {
            screen.showMovies(new ArrayList<>());
            return;
        }
        performTask(movieService.searchMovie(movieTitle),
                movieDetails -> screen.showMovies(movieDetails),
                throwable -> {
                    showError(R.string.unexpected_error_happened, MovieServiceErrorCodes.COULD_NOT_PERFORM_SEARCH, throwable);
                });
    }

    private void showError(@StringRes int resourceId, String errorCode, Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof IOException) {
            screen.showNetworkError();
        } else {
            screen.showError(context.getString(resourceId) + context.getString(R.string.error_code_with_value).replace("{code}", errorCode));
        }
    }

    private void showError(@StringRes int resourceId, Throwable throwable) {
        throwable.printStackTrace();
        screen.showError(context.getString(resourceId));
    }
}
