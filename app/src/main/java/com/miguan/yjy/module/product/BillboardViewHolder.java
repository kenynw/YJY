package com.miguan.yjy.module.product;

import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Billboard;

import butterknife.BindView;

/**
 * Copyright (c) 2017/7/27. LiaoPeiKun Inc. All rights reserved.
 */

public class BillboardViewHolder extends BaseViewHolder<Billboard> {

    @BindView(R.id.dv_billboard_top1)
    SimpleDraweeView mDvTop1;

    @BindView(R.id.dv_billboard_top2)
    SimpleDraweeView mDvTop2;

    @BindView(R.id.dv_billboard_top3)
    SimpleDraweeView mDvTop3;

    @BindView(R.id.tv_billboard_title)
    TextView mTvTitle;

    public BillboardViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_billboard);
    }

    @Override
    public void setData(Billboard data) {

    }

}
