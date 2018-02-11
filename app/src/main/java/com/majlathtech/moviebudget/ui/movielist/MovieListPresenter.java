package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.majlathtech.moviebudget.di.Network;
import com.majlathtech.moviebudget.network.interactor.MovieInteractor;
import com.majlathtech.moviebudget.network.model.Movie;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.model.MovieResponse;
import com.majlathtech.moviebudget.ui.RxPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MovieListPresenter extends RxPresenter<MovieListScreen> {
    public static final String TAG = "MovieListPresenter";

    @Inject
    Context context;

    @Inject
    MovieInteractor movieInteractor;

    @Inject
    Gson gson;

    @Inject
    @Network
    Scheduler networkScheduler;

    @Inject
    public MovieListPresenter() {
        injector.inject(this);
    }

    public void searchMovie(final String movieTitle) {
        attachSubscription(
                movieInteractor.searchMovie(movieTitle)
                .flatMap(new Function<MovieResponse, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> apply(MovieResponse movieResponse) throws Exception {
                        return Observable.fromIterable(movieResponse.getResults());// flatMap - to return details one by one from SearchListResponse
                    }}
                 )
                .flatMap(new Function<Movie, Observable<MovieDetail>>() {
                    @Override
                    public Observable<MovieDetail> apply(Movie movie) {
                        return movieInteractor.getMovieDetails(movie.getId());// and returns the corresponding getMovieDetailObservable for the specific ID
                    }
                }).subscribeOn(networkScheduler)
                .toList()//collect the unique items to a list
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MovieDetail>>() {//Consume the list
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
                                screen.showError("NetworkError");
                            }
                        }
                }));
    }

    public void getDetails(int id) {
        attachSubscription(movieInteractor.getMovieDetails(id)
                .subscribeOn(networkScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieDetail>() {
                    @Override
                    public void accept(MovieDetail movieResponse) throws Exception {
                        Log.d("down", "" + movieResponse.getBudget());
                        if (screen != null) {
                            //Todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        if (screen != null) {
                            screen.showError("NetworkError");
                        }
                    }
                }));
    }
}
