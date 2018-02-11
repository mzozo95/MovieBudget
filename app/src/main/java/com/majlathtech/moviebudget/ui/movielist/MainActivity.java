package com.majlathtech.moviebudget.ui.movielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.Movie;

import java.util.List;

import javax.inject.Inject;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MainActivity extends AppCompatActivity implements MovieListScreen{
    @Inject
    MovieListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        injector.inject(this);
        presenter.attachScreen(this);
        presenter.searchMovie("WestWorld");
    }

    @Override
    public void showMovies(List<Movie> movieList) {
        Log.d(this.getLocalClassName(), movieList.toString());
    }

    @Override
    public void showError(String errorMsg) {
        Log.d(this.getLocalClassName(), errorMsg);
    }
}
