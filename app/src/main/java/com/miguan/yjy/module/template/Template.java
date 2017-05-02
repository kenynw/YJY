package com.miguan.yjy.module.template;

import com.miguan.yjy.R;

import static com.miguan.yjy.R.layout.footer_template_0;
import static com.miguan.yjy.R.layout.footer_template_1;
import static com.miguan.yjy.R.layout.header_template_0;
import static com.miguan.yjy.R.layout.header_template_1;
import static com.miguan.yjy.R.layout.header_template_4;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public enum Template {

    TEMPLATE_0(0, R.mipmap.image_template_0, Template0ViewHolder.class, header_template_0, footer_template_0),
    TEMPLATE_1(1, R.mipmap.image_template_1, Template1ViewHolder.class, header_template_1, footer_template_1),
    TEMPLATE_2(2, R.mipmap.image_template_2, Template0ViewHolder.class, header_template_0, footer_template_0),
    TEMPLATE_3(3, R.mipmap.image_template_3, Template0ViewHolder.class, header_template_0, footer_template_0),
    TEMPLATE_4(4, R.mipmap.image_template_4, Template4ViewHolder.class, header_template_4, 0),
    TEMPLATE_5(5, R.mipmap.image_template_5, Template0ViewHolder.class, header_template_0, footer_template_0);

    public final int mIndex;

    public final int mImageRes;

    public final Class<? extends TemplateViewHolder> mClass;

    public final int mHeaderRes;

    public final int mFooterRes;

    Template(int index, int imageRes, Class<? extends TemplateViewHolder> aClass, int headerRes, int footerRes) {
        mIndex = index;
        mImageRes = imageRes;
        mClass = aClass;
        mHeaderRes = headerRes;
        mFooterRes = footerRes;
    }
}
