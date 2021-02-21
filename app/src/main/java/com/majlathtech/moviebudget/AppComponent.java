package com.majlathtech.moviebudget;

import com.majlathtech.moviebudget.network.NetworkModule;
import com.majlathtech.moviebudget.network.service.MovieService;
import com.majlathtech.moviebudget.repository.RepositoryModule;
import com.majlathtech.moviebudget.ui.UIModule;
import com.majlathtech.moviebudget.ui.composite.list.MovieListCompositeFragment;
import com.majlathtech.moviebudget.ui.movielist.MovieListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UIModule.class, NetworkModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(MovieService movieService);
    void inject(MovieListFragment movieListFragment);
    void inject(MovieListCompositeFragment movieListCompositeFragment);
}
