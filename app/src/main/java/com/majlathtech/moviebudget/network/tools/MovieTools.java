package com.majlathtech.moviebudget.network.tools;

import com.majlathtech.moviebudget.network.model.MovieDetail;

public class MovieTools {
    public static String generatePosterImageUrl(MovieDetail movieDetail) {
        //Doc: https://developers.themoviedb.org/3/getting-started/images
        String imgUrl = "";
        if (movieDetail.getPosterPath() != null) {
            imgUrl = "https://image.tmdb.org/t/p/w500/" + movieDetail.getPosterPath();
        }
        return imgUrl;
    }
}
