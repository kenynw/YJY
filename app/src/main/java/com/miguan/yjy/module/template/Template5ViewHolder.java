package com.miguan.yjy.module.template;

import android.view.ViewGroup;
import android.widget.EditText;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import butterknife.BindView;

/**
 * Copyright (c) 2017/4/24. LiaoPeiKun Inc. All rights reserved.
 */

public class Template5ViewHolder extends BaseTemplateMultiViewHolder {

    @BindView(R.id.et_template_5_content)
    EditText mEtContent;

    public Template5ViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_template_5);
    }

    @Override
    public int[] getImageResIds() {
        return new int[]{R.id.dv_template_5_image_0, R.id.dv_template_5_image_1, R.id.dv_template_5_image_2, R.id.dv_template_5_image_3};
    }

    @Override
    public int[] getFilterResIds() {
        return new int[]{R.id.iv_template_5_filter_0, R.id.iv_template_5_filter_1, R.id.iv_template_5_filter_2, R.id.iv_template_5_filter_3};
    }

    @Override
    public void initViews(Product product) {
        super.initViews(product);
        mEtContent.setText(R.string.text_template_5_content);
    }

}
