package com.majlathtech.moviebudget.network.api;

import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("/3/search/movie")
    Observable<MovieResponse> searchMovie(@Query("query") String movieName);

    @GET("/3/search/movie")
    Observable<MovieResponse> searchMovie(@Query("query") String movieName, @Query("page") int page);

    @GET("/3/movie/{movie_id}")
    Observable<MovieDetail> getMovieDetails(@Path("movie_id") int movieId);

}
