package com.majlathtech.moviebudget.ui;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxPresenter<S>  extends Presenter<S> {
    private CompositeDisposable compositeSubscription;

    public RxPresenter() {
    }

    @Override
    public void attachScreen(S screen) {
        super.attachScreen(screen);
        if (compositeSubscription != null) {
            compositeSubscription.dispose();
        }
        compositeSubscription = new CompositeDisposable();
    }


    @Override
    public void detachScreen() {
        if (compositeSubscription != null) {
            compositeSubscription.dispose();
        }
        super.detachScreen();
    }


    public void attachSubscription(Disposable subscription) {
        compositeSubscription.add(subscription);
    }
}

