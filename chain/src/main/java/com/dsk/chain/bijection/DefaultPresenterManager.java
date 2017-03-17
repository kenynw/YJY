package com.dsk.chain.bijection;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class DefaultPresenterManager extends PresenterManager {

    private Map<String, Presenter> mPresenterMap = new HashMap<>();

    private int mNextId;

    @Override
    public <PresenterType extends Presenter> PresenterType create(Object view) {
        PresenterType presenter = PresenterBuilder.fromViewClass(view.getClass());
        presenter.mId = provideId();
        mPresenterMap.put(presenter.mId, presenter);
        return presenter;
    }

    @Override
    public <PresenterType extends Presenter> PresenterType get(String id) {
        return (PresenterType) mPresenterMap.get(id);
    }

    @Override
    public void destroy(String id) {
        mPresenterMap.remove(id);
    }

    private String provideId() {
        return mNextId++ + "/" + System.nanoTime() + "/" + (int) (Math.random() * Integer.MAX_VALUE);
    }

}
