package com.miguan.yjy.module.template;

import android.view.ViewGroup;
import android.widget.EditText;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import butterknife.BindView;

/**
 * Copyright (c) 2017/5/2. LiaoPeiKun Inc. All rights reserved.
 */

public class Template3ViewHolder extends BaseTemplateMultiViewHolder {

    @BindView(R.id.et_template_3_title)
    EditText mEtTitle;

    @BindView(R.id.et_template_3_content)
    EditText mEtContent;

    public Template3ViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_template_3);
    }

    @Override
    public int[] getImageResIds() {
        return new int[]{R.id.dv_template_3_image_0, R.id.dv_template_3_image_1, R.id.dv_template_3_image_2};
    }

    @Override
    public int[] getFilterResIds() {
        return new int[]{R.id.iv_template_3_filter_0, R.id.iv_template_3_filter_1, R.id.iv_template_3_filter_2};
    }

    @Override
    public void initViews(Product product) {
        super.initViews(product);
        mEtTitle.setText(R.string.text_template_3_title);
        mEtContent.setText(R.string.text_template_3_content);
    }

}
