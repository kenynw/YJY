package com.dsk.chain.bijection;

import android.content.Intent;
import android.os.Bundle;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 * 映射Activity和Presenter的辅助类，避免View直接引用Presenter
 */
public class ViewHelper<PresenterType extends Presenter> {

    private final String KEY_PRESENTER_ID = "presenter_id";

    PresenterType mPresenter;

    private Object mView;

    public ViewHelper(Object view) {
        this.mView = view;
    }

    public PresenterType getPresenter() {
        return mPresenter;
    }

    public void onCreate(Bundle saveInstanceState) {
        String id;
        if (saveInstanceState == null || (id = saveInstanceState.getString(KEY_PRESENTER_ID)) == null) {
            mPresenter = PresenterManager.getInstance().create(mView);
            mPresenter.create(mView, saveInstanceState);
        } else {
            mPresenter = PresenterManager.getInstance().get(id);
            // 不可能为空
            if (mPresenter == null) {
                mPresenter = PresenterManager.getInstance().create(mView);
                mPresenter.create(mView, saveInstanceState);
            }
        }
    }

    public void onPostCreate() {
        mPresenter.onCreateView(mView);
    }

    public void onDestroyView() {
        mPresenter.onDestroyView();
    }

    public void onDestroy() {
        PresenterManager.getInstance().destroy(mPresenter.mId);
        mPresenter.onDestroy();
    }

    public void onSave(Bundle saveState) {
        saveState.putString(KEY_PRESENTER_ID, mPresenter.mId);
        mPresenter.onSave(saveState);
    }

    public void onResume() {
        mPresenter.onResume();
    }

    public void onPause() {
        mPresenter.onPause();
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onResult(requestCode, resultCode, data);
    }

    public void onNewIntent(Intent intent) {
        mPresenter.onNewIntent(intent);
    }

}
