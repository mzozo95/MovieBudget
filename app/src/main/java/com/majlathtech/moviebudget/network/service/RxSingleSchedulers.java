package com.majlathtech.moviebudget.network.service;

import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//src: https://github.com/droiddevgeeks/NewsApp/blob/06861a8726aaa82d2657f022bab304f109b36df8/app/src/main/java/news/agoda/com/sample/api/RxSingleSchedulers.java#L7
public interface RxSchedulers {
    RxSchedulers DEFAULT = new RxSchedulers() {
        @Override
        public <T> SingleTransformer<T, T> applySchedulers() {
            return single -> single
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
    };

    <T> SingleTransformer<T, T> applySchedulers();
}
