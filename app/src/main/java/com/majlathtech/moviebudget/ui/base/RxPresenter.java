package com.majlathtech.moviebudget.ui.base;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxPresenter<S> extends Presenter<S> {
    private CompositeDisposable compositeDisposable;

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

    protected <T> void performTask(Single<T> o, Consumer<T> onSuccess, Consumer<? super Throwable> onError) {
        attachDisposable(scheduleThreads(o).subscribe(onSuccess, onError));
    }

    protected void performTask(Completable completable, Consumer<Throwable> throwableConsumer) {
        attachDisposable(scheduleThreads(completable)
                .subscribe(() -> {
                }, throwableConsumer));
    }

    protected void performTask(Completable completable) {
        performTask(completable, Throwable::printStackTrace);
    }

    private Completable scheduleThreads(Completable o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T> Single<T> scheduleThreads(Single<T> o) {
        return o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

