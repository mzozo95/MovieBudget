package com.majlathtech.moviebudget.network.service;

import com.majlathtech.moviebudget.BuildConfig;

public class MovieServiceConfig {
    private static final String MOVIE_API_BASE_URL = "https://api.themoviedb.org";
    private static final String MOVIE_API_KEY = BuildConfig.API_KEY;
    private static String MOVIE_LANGUAGE;

    public static void init(String responseLanguage) {
        MOVIE_LANGUAGE = responseLanguage;
    }

    public static String getMovieApiBaseUrl() {
        return MOVIE_API_BASE_URL;
    }

    public static String getMovieApiKey() {
        return MOVIE_API_KEY;
    }

    public static String getMovieLanguage() {
        return MOVIE_LANGUAGE;
    }
}
