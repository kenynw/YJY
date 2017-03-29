package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Brand;

import butterknife.BindView;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandLetterViewHolder extends BrandViewHolder {

    @BindView(R.id.tv_brand_letter)
    TextView mTvLetter;

    public BrandLetterViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_brand_letter);
    }

    @Override
    public void setData(Brand data) {
        super.setData(data);
        mTvLetter.setText(data.getLetter().toUpperCase());
    }
}
