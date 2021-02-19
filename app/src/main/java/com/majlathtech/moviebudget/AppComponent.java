package com.majlathtech.moviebudget;

import com.majlathtech.moviebudget.network.NetworkModule;
import com.majlathtech.moviebudget.network.interactor.MovieService;
import com.majlathtech.moviebudget.ui.UIModule;
import com.majlathtech.moviebudget.ui.movielist.MovieListCompositeFragment;
import com.majlathtech.moviebudget.ui.movielist.MovieListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UIModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(MovieService movieService);
    void inject(MovieListFragment movieListFragment);
    void inject(MovieListCompositeFragment movieListCompositeFragment);
}
