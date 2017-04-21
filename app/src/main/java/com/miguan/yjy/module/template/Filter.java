package com.miguan.yjy.module.template;

import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;

/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */

public enum Filter {

    ORIGINAL(0, "原图", GPUImageFilter.class, true),
    INVERT(1, "反色", GPUImageColorInvertFilter.class, false),
    GRAYSCALE(2, "灰度", GPUImageGrayscaleFilter.class, false),
    PIXELATION(3, "马赛克", GPUImagePixelationFilter.class, false);

    public final int mIndex;

    public final String mName;

    public final Class<? extends GPUImageFilter> mClass;

    public boolean mIsCheck;

    Filter(int index, String name, Class<? extends GPUImageFilter> aClass, boolean isCheck, float... params) {
        mIndex = index;
        mName = name;
        mClass = aClass;
        mIsCheck = isCheck;
    }

    public static Filter getFilter(int index) {
        for (Filter filter : Filter.values()) {
            if (filter.mIndex == index) {
                return filter;
            }
        }
        return null;
    }

    public GPUImageFilter createFilter() {
        if (mClass != null) {
            try {
                return mClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
