package com.majlathtech.moviebudget;

import android.app.Application;

import com.majlathtech.moviebudget.network.service.MovieServiceConfig;
import com.majlathtech.moviebudget.network.NetworkModule;
import com.majlathtech.moviebudget.repository.RepositoryModule;
import com.majlathtech.moviebudget.ui.ContextModule;

import java.util.Locale;

public class MovieBudgetApplication extends Application {
    public static AppComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();

        MovieServiceConfig.init(Locale.getDefault().toLanguageTag());

        injector = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .networkModule(new NetworkModule())
                .repositoryModule(new RepositoryModule())
                .build();
    }
}
