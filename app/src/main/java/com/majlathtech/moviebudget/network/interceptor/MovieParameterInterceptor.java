package com.majlathtech.moviebudget.network.interceptor;


import androidx.annotation.NonNull;

import com.majlathtech.moviebudget.network.service.MovieServiceConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MovieParameterInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", MovieServiceConfig.getMovieApiKey())
                .addQueryParameter("language", MovieServiceConfig.getMovieLanguage())
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
