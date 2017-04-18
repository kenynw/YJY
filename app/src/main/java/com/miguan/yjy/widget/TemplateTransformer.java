package com.miguan.yjy.widget;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.miguan.yjy.utils.LUtils;

/**
 * 设置模板ViewPager效果
 * <p>
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplateTransformer implements ViewPager.PageTransformer {

    private final float MIN_SCALE = 0.85f;

    @Override
    public void transformPage(View page, float position) {
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
        float rotate = LUtils.dp2px(20) * Math.abs(position);
        if (position < -1) {

        } else if (position < 0) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position >= 0 && position < 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position >= 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }
    }

}
