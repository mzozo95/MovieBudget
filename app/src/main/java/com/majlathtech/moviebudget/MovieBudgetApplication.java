package com.majlathtech.moviebudget;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.majlathtech.moviebudget.ui.UIModule;

public class MovieBudgetApplication extends Application {
    public static AppComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        injector = DaggerAppComponent.builder().uIModule(new UIModule(this)).build();
    }

}
