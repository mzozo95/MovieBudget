package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.api.MovieApi;
import com.majlathtech.moviebudget.network.interactor.MovieInteractor;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.ui.RxPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class MovieListPresenter extends RxPresenter<MovieListScreen> {
    private static final String TAG = MovieListPresenter.class.getSimpleName();

    private final Context context;
    private final MovieInteractor movieInteractor;

    @Inject
    public MovieListPresenter(Context context, MovieInteractor movieInteractor) {
        this.context = context;
        this.movieInteractor = movieInteractor;
    }

    public void searchMovie(final String movieTitle) {
        performRequest(movieInteractor.searchMovie(movieTitle),
                new Consumer<List<MovieDetail>>() {
                    @Override
                    public void accept(List<MovieDetail> movieDetails) {
                        if (screen != null) {
                            screen.showMovies(movieDetails);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        if (screen != null) {
                            screen.showError(context.getString(R.string.unexpected_error_happened));
                        }
                    }
                });
    }
}
