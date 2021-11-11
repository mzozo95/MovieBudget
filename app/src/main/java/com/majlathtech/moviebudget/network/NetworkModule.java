package com.majlathtech.moviebudget.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.majlathtech.moviebudget.network.api.MovieApi;
import com.majlathtech.moviebudget.network.interceptor.MovieParameterInterceptor;
import com.majlathtech.moviebudget.network.service.MovieServiceConfig;
import com.majlathtech.moviebudget.network.service.RxSchedulers;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    public RxSchedulers provideScheduler(){
        return RxSchedulers.DEFAULT;
    }

    @Provides
    @Singleton
    public MovieApi provideMovieApi(Context context) {
        return buildRetrofit(buildHttpClient(context), MovieServiceConfig.getMovieApiBaseUrl()).create(MovieApi.class);
    }

    private Retrofit buildRetrofit(OkHttpClient client, String baseUrl) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient buildHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new MovieParameterInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(chain -> {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + 60)
                            .build();
                })
                .cache(new Cache(new File(context.getCacheDir(), "apiResponses"), 5 * 1024 * 1024))
                .addInterceptor(new ChuckInterceptor(context))
                .build();
    }
}

