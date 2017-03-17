package com.dsk.chain.expansion.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dsk.chain.R;
import com.dsk.chain.bijection.ChainFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public abstract class BaseListFragment<P extends BaseListFragmentPresenter, M> extends ChainFragment<P> {
    private ListConfig mListConfig;

    private View mRootView;

    private EasyRecyclerView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListConfig = getListConfig();
        layoutInflate(container);
        findRecycleView();
        initListView();
        initAdapter();
        return mRootView;
    }

    private void layoutInflate(ViewGroup container) {
        if (getLayout() != 0) {
            mRootView = LayoutInflater.from(getActivity()).inflate(getLayout(), container, false);
        } else if(mListConfig.mContainerLayoutRes != 0) {
            mRootView = LayoutInflater.from(getActivity()).inflate(mListConfig.mContainerLayoutRes, container, false);
        } else if(mListConfig.mContainerLayoutView != null) {
            mRootView = mListConfig.mContainerLayoutView;
        } else {
            EasyRecyclerView listView = new EasyRecyclerView(getActivity());
            listView.setId(R.id.recycle);
            listView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mRootView = listView;
        }
    }

    private void findRecycleView() {
        mListView = (EasyRecyclerView) mRootView.findViewById(R.id.recycle);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initListView() {
        if (mListConfig.mRefreshAble) mListView.setRefreshListener(getPresenter());
        if (mListConfig.mContainerErrorAble) {
            if (mListConfig.mContainerErrorView != null) mListView.setErrorView(mListConfig.mContainerErrorView);
            else if (mListConfig.mContainerErrorRes > 0) mListView.setErrorView(mListConfig.mContainerErrorRes);
        }
        if (mListConfig.mContainerEmptyAble) {
            if (mListConfig.mContainerEmptyView != null) mListView.setEmptyView(mListConfig.mContainerEmptyView);
            else if (mListConfig.mContainerEmptyRes > 0) mListView.setEmptyView(mListConfig.mContainerEmptyRes);
        }
        if (mListConfig.mContainerProgressAble) {
            if (mListConfig.mContainerProgressView != null) mListView.setProgressView(mListConfig.mContainerProgressView);
            else if (mListConfig.mContainerProgressRes > 0) mListView.setProgressView(mListConfig.mContainerProgressRes);
        }
        if (mListConfig.mHasItemDecoration) {
            if (mListConfig.mItemDecoration != null) mListView.addItemDecoration(mListConfig.mItemDecoration);
            else mListView.addItemDecoration(new DividerItemDecoration(getActivity(), mListConfig.mDecorationOrientation));
        }
    }

    private void initAdapter() {
        final BaseListFragmentPresenter.DataAdapter adapter = getPresenter().getAdapter();
        mListView.setAdapter(adapter);
        if (mListConfig.mFooterErrorAble) {
            View errorView = null;
            if (mListConfig.mFooterErrorView != null) errorView = adapter.setError(mListConfig.mFooterErrorView);
            else if (mListConfig.mFooterErrorRes > 0) errorView = adapter.setError(mListConfig.mFooterErrorRes);
            if (mListConfig.mErrorTouchToResumeAble && errorView != null) {
                errorView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.resumeMore();
                    }
                });
            }
        }
        if (mListConfig.mLoadMoreAble) {
            if (mListConfig.mFooterMoreView != null) adapter.setMore(mListConfig.mFooterMoreView, getPresenter());
            else if (mListConfig.mFooterMoreRes > 0) adapter.setMore(mListConfig.mFooterMoreRes, getPresenter());
        }
        if (mListConfig.mNoMoreAble) {
            if (mListConfig.mFooterNoMoreView != null) adapter.setNoMore(mListConfig.mFooterNoMoreView);
            else if (mListConfig.mFooterNoMoreRes > 0) adapter.setNoMore(mListConfig.mFooterNoMoreRes);
        }
    };

    public EasyRecyclerView getListView() {
        return mListView;
    }

    public void stopRefresh() {
        if (mListView != null) mListView.setRefreshing(false);
    }

    public void showError() {
        if (mListView != null) mListView.showError();
    }

    public ListConfig getListConfig() {
        return ListConfig.DEFAULT.clone();
    }

    public int getLayout() {
        return 0;
    }

    public int getViewType(int position) {
        return 0;
    }

    public abstract BaseViewHolder<M> createViewHolder(ViewGroup parent, int viewType);
}
