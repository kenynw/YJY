package com.miguan.yjy.module.template;

import android.view.ViewGroup;

import com.miguan.yjy.R;

/**
 * Copyright (c) 2017/5/2. LiaoPeiKun Inc. All rights reserved.
 */

public class Template3ViewHolder extends BaseTemplateMultiViewHolder {

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

}
