package com.miguan.yjy.module.template;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.bijection.Presenter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class GenTemplatePresenter extends Presenter<GenTemplateActivity> {

    private TemplateAdapter mAdapter;

    @Override
    protected void onCreateView(GenTemplateActivity view) {
        super.onCreateView(view);
    }

    public TemplateAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new TemplateAdapter(getView());
            mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                @Override
                public View onCreateView(ViewGroup parent) {
                    return LayoutInflater.from(getView()).inflate(R.layout.header_template_0, null);
                }

                @Override
                public void onBindView(View headerView) {

                }
            });
            mAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {
                @Override
                public View onCreateView(ViewGroup parent) {
                    return LayoutInflater.from(getView()).inflate(R.layout.footer_template_0_, null);
                }

                @Override
                public void onBindView(View headerView) {

                }
            });
            mAdapter.add(new Product());
        }
        return mAdapter;
    }

    public class TemplateAdapter extends RecyclerArrayAdapter<Product> {

        public TemplateAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return getView().createViewHolder(parent, viewType);
        }

    }
}
