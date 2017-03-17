package com.dsk.chain.expansion.overlay;

import android.app.Activity;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.ChainBaseActivity;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 * Activity对自身功能的拓展委托给该类实现
 * 可通过重写Activity的createViewExpansionDelegate提供扩展类
 */
public abstract class ViewExpansionDelegate {
    private Activity mActivity;

    private FrameLayout mContainer;

    public ViewExpansionDelegate(ChainBaseActivity activity) {
        mActivity = activity;
        mContainer = activity.getContent();
    }

    public Activity getActivity() {
        return mActivity;
    }

    public FrameLayout getContainer() {
        return mContainer;
    }

    public void showProgressBar() {}

    public void showProgressBar(String msg) {}

    public void hideProgressBar() {}

    public void showErrorPage() {}

    public void showErrorToast(String text) {}

    public void hideErrorPage() {}

    public void showEmptyPage() {}
}
