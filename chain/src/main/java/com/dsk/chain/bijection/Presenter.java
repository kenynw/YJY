package com.dsk.chain.bijection;

import android.content.Intent;
import android.os.Bundle;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class Presenter<ViewType> {

    String mId;

    private ViewType view;

    public final ViewType getView() {
        return view;
    }

    public void create(ViewType view, Bundle saveState) {
        this.view = view;
        onCreate(view, saveState);
    }

    /**
     * activity 第一次create直到到主动退出Activity之前都不会调用。
     */
    protected void onCreate(ViewType view, Bundle saveState) {

    }

    /**
     * presenter销毁时的回调。代表着activity正式退出
     */
    protected void onDestroy() {
    }

    /**
     * activity$OnCreate的回调,但执行延迟到OnCreate之后。
     */
    protected void onCreateView(ViewType view) {
        this.view = view;
    }

    protected void onDestroyView() {

    }

    protected void onResume() {
    }

    protected void onPause() {
    }

    protected void onSave(Bundle state) {
    }

    protected void onResult(int requestCode, int resultCode, Intent data) {
    }

    protected void onNewIntent(Intent intent) {

    }

}
