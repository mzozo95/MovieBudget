package com.majlathtech.moviebudget.network.api;

import com.majlathtech.moviebudget.network.NetworkConfig;
import com.majlathtech.moviebudget.network.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    String ENDPOINT_URL = NetworkConfig.BASE_URL;
    String API_KEY = NetworkConfig.API_KEY;

    @GET
    Observable<MovieResponse> searchMovie(@Query("api_key") String APP_ID,
                                          @Query("query") String unit);
}
