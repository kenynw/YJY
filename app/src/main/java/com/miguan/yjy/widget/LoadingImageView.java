package com.miguan.yjy.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.elyeproj.loaderviewlibrary.LoaderImageView;

/**
 * Copyright (c) 2017/7/5. LiaoPeiKun Inc. All rights reserved.
 */

public class LoadingImageView extends LoaderImageView {

    public LoadingImageView(Context context) {
        super(context);
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setRectColor(Paint rectPaint) {
        rectPaint.setColor(0xFFEEEEEE);
    }

}
