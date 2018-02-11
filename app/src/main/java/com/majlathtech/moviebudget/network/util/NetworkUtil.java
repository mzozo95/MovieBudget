package com.majlathtech.moviebudget.network.util;

import com.majlathtech.moviebudget.network.model.MovieDetail;

public class NetworkUtil {
    public static String getPosterImageUrl(MovieDetail movieDetail){
        //Todo get data abou the picture and download the most suitable one, Doc: https://developers.themoviedb.org/3/getting-started/images
        String imgUrl = "";
        if (movieDetail.getPoster_path()!=null){//original || w500
            imgUrl = "https://image.tmdb.org/t/p/w500/"+movieDetail.getPoster_path();
        }
        return imgUrl;
    }
}
