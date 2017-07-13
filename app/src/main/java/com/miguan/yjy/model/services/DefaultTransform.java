package com.miguan.yjy.model.services;


import android.content.Intent;

import com.miguan.yjy.model.AccountModel;
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
                .subscribeOn(Schedulers.io())
                .doOnError(throwable -> {
                    if (throwable instanceof ServiceException && ((ServiceException) throwable).getCode() == -6) {
                        AccountModel.getInstance().clearToken();
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.setAction("com.miguan.yjy.login");
                        LUtils.toast("登录信息过期");
                        LUtils.getAppContext().startActivity(intent);
                    }
                    LUtils.log("error");
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

}
