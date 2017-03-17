package com.miguan.yjy.model.services;


import com.miguan.yjy.utils.LUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class DefaultTransform<T> implements Observable.Transformer<T, T> {

    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable
                .doOnError(throwable -> LUtils.log(throwable.getLocalizedMessage()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
