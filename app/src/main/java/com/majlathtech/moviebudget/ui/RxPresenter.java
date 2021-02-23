package com.majlathtech.moviebudget.ui;

import android.util.Log;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxPresenter<S> extends Presenter<S> {
    public static final String TAG = "RxPresenter";
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

    public <T> Maybe<T> scheduleThreads(Maybe<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable scheduleThreads(Completable o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Single<T> scheduleThreads(Single<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Flowable<T> scheduleThreads(Flowable<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Observable<T> scheduleThreads(Observable<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> void performTask(Single<T> o, Consumer<T> onSuccess, Consumer<? super Throwable> onError) {
        attachDisposable(scheduleThreads(o).subscribe(onSuccess, onError));
    }

    protected <T> void performTask(Observable<T> o, Consumer<T> onSuccess, Consumer<? super Throwable> onError) {
        attachDisposable(scheduleThreads(o).subscribe(onSuccess, onError));
    }

    protected <T> void performTask(Observable<T> o, Consumer<T> s) {
        performTask(o, s, Throwable::printStackTrace);
    }

    protected void performTask(Completable completable, Consumer<Throwable> throwableConsumer) {
        long startTime = new Date().getTime();
        attachDisposable(scheduleThreads(completable)
                .subscribe(() -> {
                    Log.d(TAG, "CompletableTask finished in " + (new Date().getTime() - startTime) + "ms");
                }, throwableConsumer));
    }

    protected void performTask(Completable completable) {
        performTask(completable, Throwable::printStackTrace);
    }

    protected <T> void performTask(Runnable runnable) {
        performTask(Completable.fromRunnable(runnable));
    }
}

