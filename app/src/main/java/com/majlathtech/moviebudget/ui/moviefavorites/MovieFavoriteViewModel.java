package com.majlathtech.moviebudget.ui.moviefavorites;

import androidx.annotation.StringRes;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.repository.FavoriteDao;
import com.majlathtech.moviebudget.repository.FavoriteDatabaseErrorCodes;
import com.majlathtech.moviebudget.ui.RxTools;
import com.majlathtech.moviebudget.ui.error.UiError;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

import static com.majlathtech.moviebudget.ui.error.UiError.Type.ErrorWithCode;

public class MovieFavoriteViewModel extends ViewModel {
    private final FavoriteDao favoriteDao;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<List<MovieDetail>> favorites = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<UiError> error = new MutableLiveData<>();

    @Inject
    public MovieFavoriteViewModel(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public MutableLiveData<List<MovieDetail>> getFavorites() {
        return favorites;
    }

    public MutableLiveData<UiError> getError() {
        return error;
    }

    public void fetchFavorites() {
        loading.setValue(true);
        RxTools.performTask(disposable, favoriteDao.getAll(), movieDetails -> {
            loading.setValue(false);
            favorites.setValue(movieDetails);
        }, throwable -> {
            showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_NOT_GET_ALL, throwable);
        });
    }


    public void delete(MovieDetail element) {
        loading.setValue(true);
        RxTools.performTask(disposable,
                favoriteDao.delete(element),
                () -> {
                    loading.setValue(false);
                    fetchFavorites();
                }, throwable -> {
                    showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_REMOVE_ITEM, throwable);
                });
    }

    private void showError(@StringRes int resourceId, String errorCode, Throwable throwable) {
        loading.setValue(false);
        throwable.printStackTrace();
        error.setValue(new UiError(ErrorWithCode, resourceId, errorCode));
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
