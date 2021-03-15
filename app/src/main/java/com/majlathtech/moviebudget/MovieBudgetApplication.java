package com.majlathtech.moviebudget;

import android.app.Application;

import com.majlathtech.moviebudget.network.service.MovieServiceConfig;
import com.majlathtech.moviebudget.network.NetworkModule;
import com.majlathtech.moviebudget.repository.RepositoryModule;
import com.majlathtech.moviebudget.ui.UIModule;

import java.util.Locale;

public class MovieBudgetApplication extends Application {
    public static AppComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();

        MovieServiceConfig.init(Locale.getDefault().toLanguageTag());

        injector = DaggerAppComponent.builder()
                .uIModule(new UIModule(this))
                .networkModule(new NetworkModule(this))
                .repositoryModule(new RepositoryModule(this))
                .build();
    }
}
