package com.majlathtech.moviebudget.network.service;

import io.reactivex.CompletableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface RxSchedulers {
    RxSchedulers DEFAULT = new RxSchedulers() {
        @Override
        public <T> SingleTransformer<T, T> applySchedulers() {
            return single -> single
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public CompletableTransformer applyCompletableSchedulers() {
            return completable -> completable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    RxSchedulers TEST_SCHEDULER = new RxSchedulers() {
        @Override
        public <T> SingleTransformer<T, T> applySchedulers() {
            return single -> single
                    .subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline());
        }

        @Override
        public CompletableTransformer applyCompletableSchedulers() {
            return completable -> completable
                    .subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline());
        }
    };

    <T> SingleTransformer<T, T> applySchedulers();
    CompletableTransformer applyCompletableSchedulers();
}
