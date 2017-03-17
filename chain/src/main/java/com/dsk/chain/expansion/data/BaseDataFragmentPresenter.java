package com.dsk.chain.expansion.data;

import com.dsk.chain.bijection.Presenter;

import rx.Subscriber;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class BaseDataFragmentPresenter<V extends BaseDataFragment, M> extends Presenter<V> {

    //用于缓存数据的Subscriber
    private BehaviorSubject<M> mData = BehaviorSubject.create();

    //View的订阅关系，View被销毁时自动取消订阅。
    private Subscriber<M> mSubscriber = new Subscriber<M>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mData.onError(e);
        }

        @Override
        public void onNext(M m) {
            mData.onNext(m);
        }
    };

    private Subscription mSubscription;

    @Override
    protected void onCreateView(V view) {
        super.onCreateView(view);
        mSubscription = mData.unsafeSubscribe(new Subscriber<M>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onError(e);
            }

            @Override
            public void onNext(M m) {
                getView().setData(m);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }

    /**
     * 获取存放数据的subscriber，通常用于
     *
     *     Observable.unsafeSubscribe(getDataSubscriber());
     *
     * @return
     */
    public Subscriber<M> getSubscriber() {
        return mSubscriber;
    }

    /**
     * 获取缓存数据的Subscriber
     * 可以通过 `getDataSubject().getValue();` 获取缓存数据。
     * @return
     */
    public BehaviorSubject<M> getDataSubject() {
        return mData;
    }

    /**
     * 获取缓存数据。
     */
    public M getData() {
        return mData.getValue();
    }

    /**
     * 手动发布数据
     * @param m
     */
    public void publishData(M m) {
        mData.onNext(m);
    }

    /**
     * 手动发布错误
     * @param e
     */
    public void publishError(Throwable e) {
        mData.onError(e);
    }

}
