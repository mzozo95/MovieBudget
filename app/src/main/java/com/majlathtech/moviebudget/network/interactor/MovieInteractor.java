package com.majlathtech.moviebudget.network.interactor;

import com.majlathtech.moviebudget.network.api.MovieApi;
import com.majlathtech.moviebudget.network.model.Movie;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.model.MovieResponse;

import java.util.Collections;
import java.util.Comparator;
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
                .flatMap(new Function<MovieResponse, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> apply(MovieResponse movieResponse) {
                        return Observable.fromIterable(movieResponse.getResults());// flatMap - to return details one by one from SearchListResponse
                    }
                })
                .flatMap(new Function<Movie, Observable<MovieDetail>>() {
                    @Override
                    public Observable<MovieDetail> apply(Movie movie) {
                        return movieApi.getMovieDetails(movie.getId());// and returns the corresponding getMovieDetailObservable for the specific ID
                    }
                }).subscribeOn(Schedulers.io())
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
                }).subscribeOn(Schedulers.computation());
    }
}