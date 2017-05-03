package com.miguan.yjy.module.template;

import android.view.ViewGroup;

import com.miguan.yjy.R;

/**
 * Copyright (c) 2017/4/24. LiaoPeiKun Inc. All rights reserved.
 */

public class Template1ViewHolder extends BaseTemplateMultiViewHolder {

    public Template1ViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_template_1);
    }

    @Override
    public int[] getImageResIds() {
        return new int[]{R.id.dv_template_1_image_0, R.id.dv_template_1_image_1, R.id.dv_template_1_image_2, R.id.dv_template_1_image_3};
    }

    @Override
    public int[] getFilterResIds() {
        return new int[]{R.id.iv_template_1_filter_0, R.id.iv_template_1_filter_1, R.id.iv_template_1_filter_2, R.id.iv_template_1_filter_3};
    }

}
