package com.majlathtech.moviebudget.network.api;

import com.majlathtech.moviebudget.network.NetworkConfig;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    String ENDPOINT_URL = NetworkConfig.BASE_URL;
    String API_KEY = NetworkConfig.API_KEY;
    String LANGUAGE = "en-US";

    @GET("/3/search/movie")
    Observable<MovieResponse> searchMovie(@Query("api_key") String APP_ID,
                                          @Query("query") String unit,
                                          @Query("language") String language);

    @GET("/3/movie/{movie_id}")
    Observable<MovieDetail> getMovieDetails(@Path("movie_id") int movie_id,
                                            @Query("api_key") String APP_ID,
                                            @Query("language") String language);
}
