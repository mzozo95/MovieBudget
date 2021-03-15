package com.majlathtech.moviebudget.ui.moviefavorites;

import android.content.Context;

import androidx.annotation.StringRes;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.repository.FavoriteDao;
import com.majlathtech.moviebudget.repository.FavoriteDatabaseErrorCodes;
import com.majlathtech.moviebudget.ui.base.RxPresenter;

import javax.inject.Inject;

public class MovieFavoritesPresenter extends RxPresenter<MovieFavoritesScreen> {
    private final Context context;
    private final FavoriteDao favoriteDao;

    @Inject
    public MovieFavoritesPresenter(Context context, FavoriteDao favoriteDao) {
        this.context = context;
        this.favoriteDao = favoriteDao;
    }

    public void getFavorites() {
        performTask(favoriteDao.getAll(), screen::showFavorites, throwable -> showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_NOT_GET_ALL, throwable));
    }

    private void showError(@StringRes int resourceId, String errorCode, Throwable throwable) {
        throwable.printStackTrace();
        screen.showError(context.getString(resourceId) + context.getString(R.string.error_code_with_value).replace("{code}", errorCode));
    }
}
