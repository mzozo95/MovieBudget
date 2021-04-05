package com.majlathtech.moviebudget.ui.moviefavorites;

import android.content.Context;

import androidx.annotation.StringRes;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.repository.FavoriteDao;
import com.majlathtech.moviebudget.repository.FavoriteDatabaseErrorCodes;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieFavoriteViewModel extends ViewModel {
    //For ctx should be extnding AndroidViewModel!

    private Context context;
    private final FavoriteDao favoriteDao;

    private CompositeDisposable compositeDisposable;

    private final MutableLiveData<List<MovieDetail>> favorites = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public MovieFavoriteViewModel(Context context, FavoriteDao favoriteDao) {
        this.context = context;
        this.favoriteDao = favoriteDao;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public MutableLiveData<List<MovieDetail>> getFavorites() {
        return favorites;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void fetchFavorites() {
        loading.setValue(true);
        compositeDisposable.add(favoriteDao.getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(movieDetails -> {
                    loading.setValue(false);
                    favorites.setValue(movieDetails);
                }, throwable -> {
                    loading.setValue(false);
                    error.setValue(buildError(R.string.unexpected_error_happened, FavoriteDatabaseErrorCodes.COULD_NOT_GET_ALL, throwable));
                }));
    }

    private String buildError(@StringRes int resourceId, String errorCode, Throwable throwable) {
        throwable.printStackTrace();
        return context.getString(resourceId) + context.getString(R.string.error_code_with_value).replace("{code}", errorCode);
    }
}
