package com.majlathtech.moviebudget.network.util;

import com.majlathtech.moviebudget.network.model.MovieDetail;

public class NetworkUtil {
    public static String getPosterImageUrl(MovieDetail movieDetail){
        String imgUrl = "";
        if (movieDetail.getPoster_path()!=null){//original || w500
            imgUrl = "https://image.tmdb.org/t/p/w500/"+movieDetail.getPoster_path();
        }
        return imgUrl;
    }
}
