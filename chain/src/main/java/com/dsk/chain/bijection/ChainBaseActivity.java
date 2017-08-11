package com.dsk.chain.bijection;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dsk.chain.R;
import com.dsk.chain.expansion.overlay.DefaultViewExpansionDelegate;
import com.dsk.chain.expansion.overlay.ViewExpansionDelegate;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class ChainBaseActivity<P extends Presenter> extends ChainAppCompatActivity<P> {

    private FrameLayout mContentParent;

    protected ImmersionBar mImmersionBar;

    protected Toolbar mToolbar;

    private ViewExpansionDelegate mDelegate;

    @Override
    public void preCreatePresenter() {
        super.preCreatePresenter();
        mContentParent = (FrameLayout) findViewById(android.R.id.content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 0.2f)
                .navigationBarEnable(false);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            onSetToolbar(mToolbar);
            mImmersionBar.titleBar(mToolbar);
        }
        View statusBar = findViewById(R.id.status_bar);
        if (statusBar != null) mImmersionBar.statusBarView(statusBar);
        mImmersionBar.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImmersionBar.destroy();
    }

    public void onSetToolbar(final Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toBack();
            }
        };
        View viewBack = mToolbar.findViewById(R.id.toolbar_back);
        if (viewBack != null) {
            viewBack.setOnClickListener(onClickListener);
        } else {
            mToolbar.setNavigationOnClickListener(onClickListener);
        }
    }

    public void setToolbarTitle(int title) {
        if (mToolbar == null) {
            return;
        }
        TextView tvTitle = (TextView) findViewById(R.id.toolbar_title);
        if (tvTitle != null) tvTitle.setText(title);
    }

    public void setToolbarTitle(String title) {
        if (mToolbar == null) {
            return;
        }
        TextView tvTitle = (TextView) findViewById(R.id.toolbar_title);
        if (tvTitle != null) tvTitle.setText(title);
    }

    public TextView getTitleView() {
        if (mToolbar == null) return null;

        TextView tvTitle = (TextView) findViewById(R.id.toolbar_title);
        if (tvTitle != null) return tvTitle;
        return null;
    }

    protected void toBack() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        finish();
    }

    public FrameLayout getContent() {
        return mContentParent;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public ViewExpansionDelegate getExpansionDelegate() {
        if (mDelegate == null) mDelegate = new DefaultViewExpansionDelegate(this);
        return mDelegate;
    }

    public int[] getHideSoftViewIds() {
        return null;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (getHideSoftViewIds() == null || getHideSoftViewIds().length <= 0)
                return super.dispatchTouchEvent(ev);
            for (int id : getHideSoftViewIds()) {
                if (getCurrentFocus() != null && id == getCurrentFocus().getId()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
