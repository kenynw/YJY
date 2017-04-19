package com.miguan.yjy.module.template;

import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;

/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */

public enum Filter {
    INVERT(0, "反色", GPUImageColorInvertFilter.class, false);

    public final int mIndex;

    public final String mName;

    public final Class mClass;

    public final boolean mIsCheck;

    Filter(int index, String name, Class aClass, boolean isCheck) {
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

}
