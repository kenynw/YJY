package com.miguan.yjy.utils;

import android.content.Context;
import android.text.TextUtils;

import com.miguan.yjy.R;

import java.net.URLEncoder;

/**
 * Copyright (c) 2017/6/9. LiaoPeiKun Inc. All rights reserved.
 */

public class StringUtils {

    /**
     * 格式化报价与规格
     * @param price
     * @param form
     * @return
     */
    public static String getFormatSpec(Context context, String price, String form) {
        String spec = String.format(TextUtils.isEmpty(form)
                ? context.getString(R.string.text_product_no_spec)
                : context.getString(R.string.text_product_spec) , price, form);
        return TextUtils.isEmpty(price) || price.equals("0") || price.equals("0.00") || price.equals("暂无报价")
                ? "暂无报价" : spec;
    }

    public static String getEncodeUrl(String url) {
        char[] chars = url.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char aChar : chars) {
            if (aChar >= 0x4e00 && aChar <= 0x9fbb) {
                builder.append(URLEncoder.encode(String.valueOf(aChar)));
            } else {
                builder.append(String.valueOf(aChar));
            }
        }
        return builder.toString();
    }

}
