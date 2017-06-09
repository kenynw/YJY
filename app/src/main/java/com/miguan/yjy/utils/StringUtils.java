package com.miguan.yjy.utils;

import java.net.URLEncoder;

/**
 * Copyright (c) 2017/6/9. LiaoPeiKun Inc. All rights reserved.
 */

public class StringUtils {

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
