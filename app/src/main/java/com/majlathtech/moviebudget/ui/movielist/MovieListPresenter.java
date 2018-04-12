package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;
import android.util.Log;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.api.MovieApi;
import com.majlathtech.moviebudget.network.interactor.MovieInteractor;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.ui.RxPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class MovieListPresenter extends RxPresenter<MovieListScreen> {
    private
    static final String TAG = "MovieListPresenter";

    private final Context context;
    private final MovieInteractor movieInteractor;
    private MovieApi movieApi;


    @Inject
    public MovieListPresenter(Context context, MovieInteractor movieInteractor, MovieApi movieApi) {
        this.context = context;
        this.movieInteractor = movieInteractor;
        this.movieApi = movieApi;
    }

    public void searchMovie(final String movieTitle) {
        attachSubscription(movieInteractor.performRequest(movieApi.searchMovie(movieTitle))
                .subscribe(new Consumer<List<MovieDetail>>() {
                    @Override
                    public void accept(List<MovieDetail> movieDetails) throws Exception {
                        for (MovieDetail m:movieDetails) {
                            Log.d(TAG, "budget: "+m.getBudget());
                            Log.d(TAG, "getPoster_path: "+m.getPoster_path());
                            Log.d(TAG, "getTitle: "+m.getTitle());
                        }
                        if (screen!=null){
                            screen.showMovies(movieDetails);
                        }
                    }
                },new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                            if (screen!=null){
                                screen.showError(context.getString(R.string.unexpected_error_happened));
                            }
                        }
                }));
    }
}
