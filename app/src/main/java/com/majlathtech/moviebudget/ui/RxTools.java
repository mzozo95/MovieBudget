package com.majlathtech.moviebudget.ui;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxTools {
    public static <T> void performTask(CompositeDisposable disposable, Single<T> o, Consumer<T> onSuccess, Consumer<? super Throwable> onError) {
        disposable.add(scheduleThreads(o).subscribe(onSuccess, onError));
    }

    public static void performTask(CompositeDisposable disposable, Completable completable, Consumer<Throwable> throwableConsumer) {
        disposable.add(scheduleThreads(completable).subscribe(() -> {}, throwableConsumer));
    }

    public static void performTask(CompositeDisposable disposable, Completable completable) {
        performTask(disposable, completable, Throwable::printStackTrace);
    }

    private static Completable scheduleThreads(Completable o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static <T> Single<T> scheduleThreads(Single<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
