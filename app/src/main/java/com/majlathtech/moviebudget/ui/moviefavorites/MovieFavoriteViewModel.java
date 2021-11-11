package com.majlathtech.moviebudget.ui.moviefavorites;

import androidx.annotation.StringRes;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.service.RxSchedulers;
import com.majlathtech.moviebudget.repository.FavoriteDao;
import com.majlathtech.moviebudget.repository.FavoriteDatabaseErrorCodes;
import com.majlathtech.moviebudget.ui.error.UiError;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static com.majlathtech.moviebudget.ui.error.UiError.Type.ErrorWithCode;

public class MovieFavoriteViewModel extends ViewModel {
    private final FavoriteDao favoriteDao;
    private final RxSchedulers rxSchedulers;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<List<MovieDetail>> favorites = new MutableLiveData<>();
    private final MutableLiveData<UiError> error = new MutableLiveData<>();

    @Inject
    public MovieFavoriteViewModel(FavoriteDao favoriteDao, RxSchedulers rxSchedulers) {
        this.favoriteDao = favoriteDao;
        this.rxSchedulers = rxSchedulers;
    }

    public MutableLiveData<List<MovieDetail>> getFavorites() {
        return favorites;
    }

    public MutableLiveData<UiError> getError() {
        return error;
    }

    public void fetchFavorites() {
        disposable.add(favoriteDao.getAll()
                .compose(rxSchedulers.applySchedulers())
                .subscribe(favorites::setValue, throwable -> {
                    showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_NOT_GET_ALL, throwable);
                }));
    }


    public void delete(MovieDetail element) {
        disposable.add(
                favoriteDao.delete(element)
                        .compose(rxSchedulers.applyCompletableSchedulers())
                        .subscribe(
                                this::fetchFavorites, throwable -> {
                                    showError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_REMOVE_ITEM, throwable);
                                }));
    }

    private void showError(@StringRes int resourceId, String errorCode, Throwable throwable) {
        throwable.printStackTrace();
        error.setValue(new UiError(ErrorWithCode, resourceId, errorCode));
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
