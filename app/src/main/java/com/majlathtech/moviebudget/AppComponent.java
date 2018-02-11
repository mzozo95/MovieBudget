package com.majlathtech.moviebudget;

import com.majlathtech.moviebudget.ui.UIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UIModule.class})
public interface AppComponent {
}
