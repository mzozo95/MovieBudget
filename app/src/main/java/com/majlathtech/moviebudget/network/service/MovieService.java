package com.majlathtech.moviebudget.network.service;

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

public class MovieService {
    final MovieApi movieApi;

    @Inject
    public MovieService(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    //Fetch more pages recursively
    private Observable<MovieResponse> getPageAndNext(String movieName, int page, int maxPageNumber) {
        return movieApi.searchMovie(movieName, page)
                .concatMap((Function<MovieResponse, Observable<MovieResponse>>) movieResponse -> {
                    if (movieResponse.getTotalPages() == movieResponse.getPage() || movieResponse.getPage() == maxPageNumber) {
                        return Observable.just(movieResponse);
                    }
                    return Observable.just(movieResponse).concatWith(getPageAndNext(movieName, movieResponse.getPage() + 1, maxPageNumber));
                });
    }

    public Single<List<MovieDetail>> searchMovie(String movieName) {
        return getPageAndNext(movieName, 1, 3)
                .flatMap((Function<MovieResponse, Observable<Movie>>) movieResponse -> Observable.fromIterable(movieResponse.getResults()))// flatMap - to return details one by one from SearchListResponse
                .flatMap((Function<Movie, Observable<MovieDetail>>) movie -> movieApi.getMovieDetails(movie.getId()))// and returns the corresponding getMovieDetailObservable for the specific ID
                .subscribeOn(Schedulers.io())
                .toSortedList((o1, o2) -> o2.getBudget() - o1.getBudget())
                .subscribeOn(Schedulers.computation());
    }
}