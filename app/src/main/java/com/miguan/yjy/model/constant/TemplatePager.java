package com.miguan.yjy.model.constant;

import com.miguan.yjy.R;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public enum  TemplatePager {

    TEMPLATE_0(0, R.layout.item_list_template_0, R.mipmap.image_template_1),
    TEMPLATE_1(1, R.layout.item_list_template_0, R.mipmap.image_template_1),
    TEMPLATE_2(2, R.layout.item_list_template_0, R.mipmap.image_template_1);

    public final int mIndex;

    public final int mLayoutRes;

    public final int mImageRes;

    TemplatePager(int index, int layoutRes, int imageRes) {
        mIndex = index;
        mLayoutRes = layoutRes;
        mImageRes = imageRes;
    }

}
