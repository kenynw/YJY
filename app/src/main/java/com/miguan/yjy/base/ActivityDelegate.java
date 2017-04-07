package com.miguan.yjy.base;

import android.app.Activity;

import com.dsk.chain.bijection.ActivityLifeCycleDelegate;
import com.umeng.analytics.MobclickAgent;

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
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
}
