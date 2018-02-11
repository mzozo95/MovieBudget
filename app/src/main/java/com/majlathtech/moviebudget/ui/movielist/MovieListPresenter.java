package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.majlathtech.moviebudget.di.Network;
import com.majlathtech.moviebudget.network.interactor.MovieInteractor;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.model.MovieResponse;
import com.majlathtech.moviebudget.ui.RxPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MovieListPresenter extends RxPresenter<MovieListScreen> {
    @Inject
    Context context;

    @Inject
    MovieInteractor movieInteractor;

    @Inject
    Gson gson;

    @Inject
    @Network
    Scheduler networkScheduler;

    @Inject
    public MovieListPresenter() {injector.inject(this);}

    public void searchMovie(String movieTitle) {
        attachSubscription(movieInteractor.searchMovie(movieTitle)
                .subscribeOn(networkScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        Log.d("down",""+ movieResponse.getPage());
                        //Todo fetch budget parameter
                        if (screen!=null){
                            screen.showMovies(movieResponse.getResults());
                        }
                        getDetails(2362);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        if (screen!=null){
                            screen.showError("NetworkError");
                        }
                    }
                }));
    }

    public void getDetails(int id) {
        attachSubscription(movieInteractor.getMovieDetails(id)
                .subscribeOn(networkScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieDetail>() {
                    @Override
                    public void accept(MovieDetail movieResponse) throws Exception {
                        Log.d("down",""+ movieResponse.getBudget());
                        if (screen!=null){
                            //Todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        if (screen!=null){
                            screen.showError("NetworkError");
                        }
                    }
                }));
    }
}
