package com.majlathtech.moviebudget.network.interactor;

import com.majlathtech.moviebudget.network.api.MovieApi;
import com.majlathtech.moviebudget.network.model.Movie;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.model.MovieResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
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

    public Single<List<MovieDetail>> searchMovie(String movieName) {
        return movieApi.searchMovie(movieName)
                .flatMap((Function<MovieResponse, Observable<Movie>>) movieResponse -> {
                    return Observable.fromIterable(movieResponse.getResults());// flatMap - to return details one by one from SearchListResponse
                })
                .flatMap((Function<Movie, Observable<MovieDetail>>) movie -> {
                    return movieApi.getMovieDetails(movie.getId());// and returns the corresponding getMovieDetailObservable for the specific ID
                }).subscribeOn(Schedulers.io())
                .toList()
                .map(movieDetails -> {
                    movieDetails.sort((movieDetail, t1) -> t1.getBudget() - movieDetail.getBudget());
                    return movieDetails;
                }).subscribeOn(Schedulers.computation());
    }
}