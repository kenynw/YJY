package com.miguan.yjy.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * @作者 cjh
 * @日期 2017/3/27 14:24
 * @描述 SpanUtils
 */

public class SpanUtils {
  /**
   *
   * @param text
   * @param color
   * */
    public  static  SpannableStringBuilder addColor(CharSequence text, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        if (color != 0) {
            builder.setSpan(new ForegroundColorSpan(color), 0, text.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }
}
