package com.miguan.yjy.module.template;

import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class Template4ViewHolder extends BaseTemplateMultiViewHolder {

    public Template4ViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_template_4);

        int width = (LUtils.getScreenWidth() - LUtils.dp2px(65)) / 3;
        for (int i = 0; i < mDvImages.size(); i++) {
            SimpleDraweeView dvImage = mDvImages.get(i);
            int size = i == 0 ? width * 2 : width;
            ViewGroup.LayoutParams layoutParams = dvImage.getLayoutParams();
            layoutParams.height = size;
            dvImage.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int[] getImageResIds() {
        return new int[]{R.id.dv_template_4_image_0, R.id.dv_template_4_image_1, R.id.dv_template_4_image_2};
    }

    @Override
    public int[] getFilterResIds() {
        return new int[]{R.id.iv_template_4_filter_0, R.id.iv_template_4_filter_1, R.id.iv_template_4_filter_2};
    }

}
