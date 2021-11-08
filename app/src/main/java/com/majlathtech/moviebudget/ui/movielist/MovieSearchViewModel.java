package com.majlathtech.moviebudget.ui.movielist;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.service.MovieService;
import com.majlathtech.moviebudget.network.service.MovieServiceErrorCodes;
import com.majlathtech.moviebudget.repository.FavoriteDao;
import com.majlathtech.moviebudget.repository.FavoriteDatabaseErrorCodes;
import com.majlathtech.moviebudget.ui.RxTools;
import com.majlathtech.moviebudget.ui.error.NetworkError;
import com.majlathtech.moviebudget.ui.error.UiError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static com.majlathtech.moviebudget.ui.error.UiError.Type.ErrorWithCode;

public class MovieSearchViewModel extends ViewModel {
    private final MovieService movieService;
    private final FavoriteDao favoriteDao;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<List<MovieDetail>> movies = new MutableLiveData<>();
    private final MutableLiveData<List<MovieDetail>> favorites = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<UiError> error = new MutableLiveData<>();

    @Inject
    public MovieSearchViewModel(MovieService movieService, FavoriteDao favoriteDao) {
        this.movieService = movieService;
        this.favoriteDao = favoriteDao;
    }

    public MutableLiveData<List<MovieDetail>> getMovies() {
        return movies;
    }

    public MutableLiveData<List<MovieDetail>> getFavorites() {
        return favorites;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<UiError> getError() {
        return error;
    }

    public void addToFavorites(@NonNull MovieDetail... favoriteMovieElements) {
        RxTools.performTask(disposable, favoriteDao.insertAll(favoriteMovieElements), throwable -> showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_INSERT_ITEM, throwable));
    }

    public void removeFromFavorites(@NonNull MovieDetail favoriteMovieElement) {
        RxTools.performTask(disposable, favoriteDao.delete(favoriteMovieElement), throwable -> showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_REMOVE_ITEM, throwable));
    }

    public void fetchFavorites() {
        loading.setValue(true);
        RxTools.performTask(disposable, favoriteDao.getAll(),
                favoriteResult -> {
                    loading.setValue(false);
                    favorites.setValue(favoriteResult);
                },
                throwable -> showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_NOT_GET_ALL, throwable));
    }

    public void searchMovie(final String movieTitle) {
        if (movieTitle == null || movieTitle.isEmpty()) {
            movies.setValue(new ArrayList<>());
            return;
        }

        loading.setValue(true);
        RxTools.performTask(disposable, movieService.searchMovie(movieTitle),
                movieDetails -> {
                    loading.setValue(false);
                    movies.setValue(movieDetails);
                },
                throwable -> {
                    showError(R.string.unexpected_error_happened, MovieServiceErrorCodes.COULD_NOT_PERFORM_SEARCH, throwable);
                });
    }

    private void showError(@StringRes int resourceId, String errorCode, Throwable throwable) {
        loading.setValue(false);
        throwable.printStackTrace();
        if (throwable instanceof IOException) {
            error.setValue(new NetworkError());
        } else {
            error.setValue(new UiError(ErrorWithCode, resourceId, errorCode));
        }
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
