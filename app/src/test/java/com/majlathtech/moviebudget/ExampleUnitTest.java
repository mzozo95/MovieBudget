package com.majlathtech.moviebudget;

import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.util.MovieUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void imageURLTest() {
        MovieDetail movieDetail = new MovieDetail();
        movieDetail.posterPath = "8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg";
        assertEquals(MovieUtil.generatePosterImageUrl(movieDetail), "https://image.tmdb.org/t/p/w500/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg");
    }
}