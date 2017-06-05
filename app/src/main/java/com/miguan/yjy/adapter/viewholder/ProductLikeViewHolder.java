package com.miguan.yjy.adapter.viewholder;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.product.ProductDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class ProductLikeViewHolder extends BaseViewHolder<Product> {

    @BindView(R.id.dv_product_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_product_name)
    TextView mTvName;

    public ProductLikeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_product_like);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Product data) {
        mDvThumb.setImageURI(Uri.parse(data.getProduct_img()));
        mTvName.setText(data.getProduct_name());
        itemView.setOnClickListener(v -> ProductDetailPresenter.start(getContext(), data.getId()));
    }

}
