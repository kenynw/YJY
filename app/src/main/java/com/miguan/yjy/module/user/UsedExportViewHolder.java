package com.miguan.yjy.module.user;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.UserProduct;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/9/27. LiaoPeiKun Inc. All rights reserved.
 */

public class UsedExportViewHolder extends BaseViewHolder<UserProduct> {

    @BindView(R.id.dv_product_used_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_product_used_name)
    TextView mTvName;

    @BindView(R.id.tv_product_used_residue)
    TextView mTvResidue;

    public UsedExportViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_used_export);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(UserProduct data) {
        mDvThumb.setImageURI(Uri.parse(data.getImg()));
        String title = data.getProduct().contains(data.getBrand_name()) ? data.getProduct()
                : data.getBrand_name() + data.getProduct();
        mTvName.setText(title);
        mTvResidue.setText(data.getDays() + "天");
    }

}
