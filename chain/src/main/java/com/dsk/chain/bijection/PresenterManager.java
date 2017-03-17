package com.dsk.chain.bijection;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public abstract class PresenterManager {
    private static PresenterManager mInstance = new DefaultPresenterManager();

    public static PresenterManager getInstance() {
        return mInstance;
    }

    public abstract  <PresenterType extends Presenter> PresenterType create(Object view);

    public abstract  <PresenterType extends Presenter> PresenterType get(String id);

    public abstract void destroy(String id);

}
