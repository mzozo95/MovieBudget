package com.majlathtech.moviebudget;

import com.majlathtech.moviebudget.network.NetworkModule;
import com.majlathtech.moviebudget.network.service.MovieService;
import com.majlathtech.moviebudget.repository.RepositoryModule;
import com.majlathtech.moviebudget.ui.UIModule;
import com.majlathtech.moviebudget.ui.movielist.MovieSearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UIModule.class, NetworkModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(MovieService movieService);

    void inject(MovieSearchFragment movieSearchFragment);
}
