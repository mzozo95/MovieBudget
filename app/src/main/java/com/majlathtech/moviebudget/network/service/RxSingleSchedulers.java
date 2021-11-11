package com.majlathtech.moviebudget.network.service;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface RxSingleSchedulers  {
    RxSingleSchedulers  DEFAULT = new RxSingleSchedulers () {
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

    RxSingleSchedulers  TEST_SCHEDULER = new RxSingleSchedulers () {
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
