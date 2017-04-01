package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class ProductLikeViewHolder extends BaseViewHolder<Product> {

    @BindView(R.id.iv_product_thumb)
    ImageView mIvThumb;

    @BindView(R.id.tv_product_name)
    TextView mTvName;

    public ProductLikeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_product_like);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Product data) {
        mTvName.setText(data.getProduct_name());
    }
}
