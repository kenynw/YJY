package com.dsk.chain.bijection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dsk.chain.R;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class ChainFragment<PresenterType extends Presenter> extends Fragment {

    private ViewHelper<PresenterType> mHelper = new ViewHelper<>(this);

    private ImmersionBar mImmersionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImmersionBar = ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 0.2f)
                .navigationBarEnable(false);
        View status = view.findViewById(R.id.status_bar);
        if (status != null) mImmersionBar.statusBarView(status);
        mImmersionBar.init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mHelper.onSave(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHelper.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHelper.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHelper.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mHelper.onResult(requestCode, resultCode, data);
    }

    public PresenterType getPresenter() {
        return mHelper.getPresenter();
    }

}
