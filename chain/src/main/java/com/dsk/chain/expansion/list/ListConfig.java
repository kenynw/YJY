package com.dsk.chain.expansion.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dsk.chain.R;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class ListConfig implements Cloneable {

    public static ListConfig DEFAULT = new ListConfig();

    public static void setDefaultListConfig(ListConfig config){
        DEFAULT = config;
    }

    boolean mRefreshAble = true;

    boolean mErrorTouchToResumeAble = false;

    boolean mPaddingNavigationBarAble = false;

    View mContainerLayoutView;
    int mContainerLayoutRes = 0;

    boolean mContainerEmptyAble = true;
    View mContainerEmptyView;
    int mContainerEmptyRes = R.layout.default_view_list_empty;

    boolean mContainerProgressAble = false;
    View mContainerProgressView;
    int mContainerProgressRes = R.layout.default_view_list_progress;

    boolean mContainerErrorAble = true;
    View mContainerErrorView;
    int mContainerErrorRes = R.layout.default_view_list_error;

    boolean mLoadMoreAble = true;
    View mFooterMoreView;
    int mFooterMoreRes = R.layout.default_footer_load_more;

    boolean mNoMoreAble = true;
    View mFooterNoMoreView;
    int mFooterNoMoreRes = R.layout.default_footer_no_more;

    boolean mFooterErrorAble = true;
    View mFooterErrorView;
    int mFooterErrorRes = R.layout.default_footer_load_error;

    boolean mHasItemDecoration = true;
    RecyclerView.ItemDecoration mItemDecoration;
    int mDecorationOrientation = LinearLayoutManager.VERTICAL;

    public ListConfig setContainerLayoutView(View mContainerLayoutView) {
        this.mContainerLayoutView = mContainerLayoutView;
        return this;
    }

    public ListConfig setContainerLayoutRes(int mContainerLayoutRes) {
        this.mContainerLayoutRes = mContainerLayoutRes;
        return this;
    }
    public ListConfig setFooterErrorAble(boolean mErrorAble) {
        this.mFooterErrorAble = mErrorAble;
        return this;
    }

    public ListConfig setFooterErrorView(View mErrorView) {
        this.mFooterErrorView = mErrorView;
        return this;
    }

    public ListConfig setFooterView(View footerView) {
        this.mFooterErrorView = footerView;
        this.mFooterMoreView = footerView;
        this.mFooterNoMoreView = footerView;
        return this;
    }

    public ListConfig setErrorRes(int mErrorRes) {
        this.mFooterErrorRes = mErrorRes;
        return this;
    }

    public ListConfig setRefreshAble(boolean mRefreshAble) {
        this.mRefreshAble = mRefreshAble;
        return this;
    }

    public ListConfig setLoadMoreAble(boolean mLoadmoreAble) {
        this.mLoadMoreAble = mLoadmoreAble;
        return this;
    }

    public ListConfig setFooterMoreView(View mLoadMoreView) {
        this.mFooterMoreView = mLoadMoreView;
        return this;
    }

    public ListConfig setFooterMoreRes(int mLoadMoreRes) {
        this.mFooterMoreRes = mLoadMoreRes;
        return this;
    }

    public ListConfig setFooterNoMoreView(View mNoMoreView) {
        this.mFooterNoMoreView = mNoMoreView;
        return this;
    }

    public ListConfig setNoMoreAble(boolean mNoMoreAble) {
        this.mNoMoreAble = mNoMoreAble;
        return this;
    }
    public ListConfig setFooterNoMoreRes(int mMoMoreRes) {
        this.mFooterNoMoreRes = mMoMoreRes;
        return this;
    }

    public ListConfig setContainerEmptyView(View mContainerEmptyView) {
        this.mContainerEmptyView = mContainerEmptyView;
        return this;
    }

    public ListConfig setContainerEmptyRes(int mContainerEmptyRes) {
        this.mContainerEmptyRes = mContainerEmptyRes;
        return this;
    }

    public ListConfig setContainerProgressView(View mContainerProgressView) {
        this.mContainerProgressView = mContainerProgressView;
        return this;
    }

    public ListConfig setContainerProgressRes(int mContainerProgressRes) {
        this.mContainerProgressRes = mContainerProgressRes;
        return this;
    }

    public ListConfig setContainerErrorView(View mContainerErrorView) {
        this.mContainerErrorView = mContainerErrorView;
        return this;
    }
    public ListConfig setContainerErrorRes(int mContainerErrorRes) {
        this.mContainerErrorRes = mContainerErrorRes;
        return this;
    }

    public ListConfig setContainerEmptyAble(boolean containerEmptyAble) {
        mContainerEmptyAble = containerEmptyAble;
        return this;
    }

    public ListConfig setContainerProgressAble(boolean mContainerProgressAble) {
        this.mContainerProgressAble = mContainerProgressAble;
        return this;
    }

    public ListConfig setContainerNoMoreAble(boolean mContainerNoMoreAble) {
        this.mContainerEmptyAble = mContainerNoMoreAble;
        return this;
    }

    public ListConfig setContainerErrorAble(boolean mContainerErrorAble) {
        this.mContainerErrorAble = mContainerErrorAble;
        return this;
    }

    public ListConfig setPaddingNavigationBarAble(boolean mPaddingNavigationBarAble){
        this.mPaddingNavigationBarAble = mPaddingNavigationBarAble;
        return this;
    }

    public ListConfig hasItemDecoration(boolean hasItemDecoration) {
        mHasItemDecoration = hasItemDecoration;
        return this;
    }

    public ListConfig setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.mItemDecoration= itemDecoration;
        return this;
    }

    public ListConfig setDecorationOrientation(int decorationOrientation) {
        mDecorationOrientation = decorationOrientation;
        return this;
    }

    @Override
    public ListConfig clone(){
        try {
            return (ListConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new ListConfig();
    }
}
