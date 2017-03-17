package com.dsk.chain.model;

import android.content.Context;

/**
 * Copyright (c) 2017/1/4. LiaoPeiKun Inc. All rights reserved.
 */

public abstract class AbsModel {

    protected static <T extends AbsModel> T getInstance(Class<T> clazz) {
        return ModelManager.getInstance(clazz);
    }

    protected void onAppCreate(Context context) {}

    protected void onAppCreateOnBackThread(Context context) {}

    protected final void runOnBackThread(Runnable runnable) {
        ModelManager.runOnBackThread(runnable);
    }
}
