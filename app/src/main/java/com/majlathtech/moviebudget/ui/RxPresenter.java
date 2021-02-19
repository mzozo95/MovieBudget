package com.majlathtech.moviebudget.ui;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

    public void attachDisposable(Disposable subscription) {
        compositeSubscription.add(subscription);
    }

    private <T> Single<T> scheduleThreads(Single<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T> Observable<T> scheduleThreads(Observable<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> void performRequest(Single<T> o, Consumer<T> onSuccess, Consumer<? super Throwable> onError) {
        attachDisposable(scheduleThreads(o).subscribe(onSuccess, onError));
    }

    protected <T> void performRequest(Observable<T> o, Consumer<T> onSuccess, Consumer<? super Throwable> onError) {
        attachDisposable(scheduleThreads(o).subscribe(onSuccess, onError));
    }

    protected <T> void performRequest(Observable<T> o, Consumer<T> s) {
        attachDisposable(scheduleThreads(o).subscribe(s, Throwable::printStackTrace));
    }
}

