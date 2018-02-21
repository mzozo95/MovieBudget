package com.majlathtech.moviebudget.network;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.majlathtech.moviebudget.network.api.MovieApi;
import com.majlathtech.moviebudget.network.interceptor.HeaderInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private Context context;

    public NetworkModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new ChuckInterceptor(context))
                .build();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(NetworkConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public MovieApi providePoiApi(Retrofit retrofit) {
        return retrofit.create(MovieApi.class);
    }


    @Provides
    @Singleton
    public Gson provideGson(){
        return new GsonBuilder().create();
    }
}

