package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class UsedViewHolder extends BaseViewHolder<Product> {

    @BindView(R.id.tv_product_used_open)
    TextView mTvOpen;

    @BindView(R.id.tv_product_used_delete)
    TextView mTvDelete;

    @BindView(R.id.iv_product_used_thumb)
    ImageView mIvThumb;

    @BindView(R.id.tv_product_used_name)
    TextView mTvName;

    @BindView(R.id.tv_product_used_date)
    TextView mTvDate;

    @BindView(R.id.tv_product_used_residue)
    TextView mTvResidue;

    public UsedViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_product_used);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Product data) {
        Glide.with(getContext())
                .load(data.getProduct_img())
                .placeholder(R.mipmap.def_image_loading)
                .error(R.mipmap.def_image_loading)
                .into(mIvThumb);

        mTvName.setText(data.getProduct_name());
        mTvDate.setText(data.getProduct_date());

        mTvDelete.setOnClickListener(v -> {
            LUtils.toast("删除");
        });
        mTvOpen.setOnClickListener(v -> {
            LUtils.toast("开封");
        });
    }
}
