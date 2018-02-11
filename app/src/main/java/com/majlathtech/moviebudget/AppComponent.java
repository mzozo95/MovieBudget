package com.majlathtech.moviebudget;

import com.majlathtech.moviebudget.network.NetworkModule;
import com.majlathtech.moviebudget.network.interactor.MovieInteractor;
import com.majlathtech.moviebudget.ui.UIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UIModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(MovieInteractor movieInteractor);
}
