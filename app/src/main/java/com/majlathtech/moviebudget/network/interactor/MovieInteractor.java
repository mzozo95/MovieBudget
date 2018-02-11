package com.majlathtech.moviebudget.network.interactor;

import com.majlathtech.moviebudget.network.api.MovieApi;
import com.majlathtech.moviebudget.network.model.MovieResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MovieInteractor {
    @Inject
    MovieApi movieApi;

    @Inject
    public MovieInteractor() {
        injector.inject(this);
    }

    public Observable<MovieResponse> searchMovie(String searchKey){
        return movieApi.searchMovie(MovieApi.API_KEY, searchKey);
    }
}
