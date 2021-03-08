package com.majlathtech.moviebudget.network.util;

import com.majlathtech.moviebudget.network.model.MovieDetail;

public class MovieUtil {
    public static String generatePosterImageUrl(MovieDetail movieDetail) {
        //Doc: https://developers.themoviedb.org/3/getting-started/images
        String imgUrl = "";
        if (movieDetail.posterPath != null) {
            imgUrl = "https://image.tmdb.org/t/p/w500/" + movieDetail.posterPath;
        }
        return imgUrl;
    }
}
