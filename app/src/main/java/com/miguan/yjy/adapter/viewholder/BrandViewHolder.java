package com.miguan.yjy.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Brand;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandViewHolder extends BaseViewHolder<Brand> {

    @BindView(R.id.tv_brand_name)
    TextView mTvName;

    @BindView(R.id.iv_brand_delete)
    ImageView mIvDelete;

    private OnBrandDeleteListener mListener;

    public BrandViewHolder(ViewGroup parent) {
        this(parent, R.layout.item_list_brand);
    }

    public BrandViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Brand data) {
        mTvName.setText(data.getName());
        mIvDelete.setOnClickListener(v -> {
            if (mListener != null) mListener.onBrandDelete(data);
        });
        mIvDelete.setVisibility(data.isLocal() ? View.VISIBLE : View.GONE);
    }

    public void setOnBrandDeleteListener(OnBrandDeleteListener listener) {
        mListener = listener;
    }

    public interface OnBrandDeleteListener {
        void onBrandDelete(Brand brand);
    }

}
