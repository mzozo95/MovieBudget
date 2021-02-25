package com.majlathtech.moviebudget.ui.base;

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
    private CompositeDisposable compositeDisposable;

    public RxPresenter() {
    }

    @Override
    public void attachScreen(S screen) {
        super.attachScreen(screen);
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachScreen() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        super.detachScreen();
    }

    public void attachDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    private <T> Maybe<T> scheduleThreads(Maybe<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Completable scheduleThreads(Completable o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T> Single<T> scheduleThreads(Single<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T> Flowable<T> scheduleThreads(Flowable<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T> Observable<T> scheduleThreads(Observable<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> void performTask(Single<T> o, Consumer<T> onSuccess, Consumer<? super Throwable> onError) {
        long startTime = new Date().getTime();
        attachDisposable(scheduleThreads(o).subscribe(t -> {
            Log.d(TAG, "Single task finished in " + (new Date().getTime() - startTime) + "ms");
            onSuccess.accept(t);
        }, onError));
    }

    protected <T> void performTask(Observable<T> o, Consumer<T> onSuccess, Consumer<? super Throwable> onError) {
        long startTime = new Date().getTime();
        attachDisposable(scheduleThreads(o).subscribe(t -> {
            Log.d(TAG, "Observable task finished in " + (new Date().getTime() - startTime) + "ms");
            onSuccess.accept(t);
        }, onError));
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

    protected void performTask(Runnable runnable) {
        performTask(Completable.fromRunnable(runnable));
    }
}

