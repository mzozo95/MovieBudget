package com.majlathtech.moviebudget.network.interactor;

import com.majlathtech.moviebudget.network.api.MovieApi;
import com.majlathtech.moviebudget.network.model.Movie;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.model.MovieResponse;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MovieInteractor {
    @Inject
    MovieApi movieApi;

    @Inject
    public MovieInteractor() {
        injector.inject(this);
    }

    public Single<List<MovieDetail>> performRequest(Observable<MovieResponse> observable) {
        return observable
                .flatMap(new Function<MovieResponse, Observable<Movie>>() {
                              @Override
                              public Observable<Movie> apply(MovieResponse movieResponse) throws Exception {
                                  return Observable.fromIterable(movieResponse.getResults());// flatMap - to return details one by one from SearchListResponse
                              }
                })
                .flatMap(new Function<Movie, Observable<MovieDetail>>() {
                            @Override
                            public Observable<MovieDetail> apply(Movie movie) {
                                return movieApi.getMovieDetails(movie.getId());// and returns the corresponding getMovieDetailObservable for the specific ID
                            }
                }).subscribeOn(Schedulers.io())
                .concatMapIterable(new Function<MovieDetail, Iterable<MovieDetail>>() {
                    @Override
                    public Iterable<MovieDetail> apply(MovieDetail movieDetail) throws Exception {
                        List<MovieDetail> list = new ArrayList<>();
                        for (int i = 0; i < 2; i++) {
                            list.add(movieDetail);
                        }
                        return list;
                    }
                })
                .toList()
                .map(new Function<List<MovieDetail>, List<MovieDetail>>() {
                    @Override
                    public List<MovieDetail> apply(List<MovieDetail> movieDetails) throws Exception {
                        Collections.sort(movieDetails, new Comparator<MovieDetail>() {
                            @Override
                            public int compare(MovieDetail movieDetail, MovieDetail t1) {
                                return t1.getBudget() - movieDetail.getBudget();
                            }
                        });
                        return movieDetails;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }
}