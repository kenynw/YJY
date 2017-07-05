package com.dsk.chain.expansion.overlay;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsk.chain.R;
import com.dsk.chain.bijection.ChainBaseActivity;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class DefaultViewExpansionDelegate extends ViewExpansionDelegate {

    private ProgressDialog mProgressDialog;

    private View mLoadingView;

    private TextView mTvEmpty;

    private View mContent;

    public DefaultViewExpansionDelegate(ChainBaseActivity activity) {
        super(activity);
    }

    @Override
    public void showProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mContent = getContainer().findViewById(R.id.layout_content);
        if (mContent != null) mContent.setVisibility(View.INVISIBLE);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("请稍等");
        mProgressDialog.show();
    }

    @Override
    public void showProgressBar(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mContent = getContainer().findViewById(R.id.layout_content);
        if (mContent != null) mContent.setVisibility(View.INVISIBLE);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    @Override
    public void showLoadingView(int resId) {
        if (mLoadingView == null) {
            mLoadingView = getActivity().getLayoutInflater().inflate(resId <= 0 ? R.layout.default_view_list_progress : resId, getContainer());
            mLoadingView.setVisibility(View.GONE);
            if (mLoadingView.getParent() == null) getContainer().addView(mLoadingView);
        } else {
            mLoadingView.setVisibility(View.VISIBLE);
        }
//        setToolbar(mLoadingView);
    }

    @Override
    public void hideLoadingView() {
        if (mLoadingView != null) getContainer().removeView(mLoadingView);
    }

    @Override
    public void hideProgressBar() {
        if (mProgressDialog != null) mProgressDialog.dismiss();
        if (mContent != null) mContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorPage() {
        super.showErrorPage();
    }

    @Override
    public void showErrorToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideErrorPage() {
        super.hideErrorPage();
    }

    @Override
    public void showEmptyPage() {
        if (mTvEmpty == null) {
            mTvEmpty = new TextView(getActivity());
            mTvEmpty.setText(R.string.text_empty);
            mTvEmpty.setTextSize(R.dimen.text_size_body_material);
            mTvEmpty.setTextColor(getActivity().getResources().getColor(R.color.text_black_primary));
            getContainer().addView(mTvEmpty, getLayoutParams());
        } else {
            mTvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar!=null){
            getActivity().setSupportActionBar(toolbar);
            getActivity().onSetToolbar(toolbar);
        }
    }

    private FrameLayout.LayoutParams getLayoutParams() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        return lp;
    }

}
