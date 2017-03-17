package com.miguan.yjy.base;

import android.app.Activity;
import android.os.Bundle;

import com.dsk.chain.bijection.ActivityLifeCycleDelegate;

/**
 * 生命周期代理类，友盟统计可以在此实现
 *
 * Copyright (c) 2017/2/14. LiaoPeiKun Inc. All rights reserved.
 */
public class ActivityDelegate extends ActivityLifeCycleDelegate {

    public ActivityDelegate(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
