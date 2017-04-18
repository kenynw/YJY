package com.miguan.yjy.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Patterns;

import java.util.regex.Matcher;

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

    public static SpannableString getContentSpannable(String content) {
        SpannableString spannableString = new SpannableString(content);
        Matcher matcher = Patterns.WEB_URL.matcher(content);
        while (matcher.find()) {
            String urlStr = matcher.group();
            LUtils.log("url: " + urlStr);
            int start = matcher.start();

            URLSpan urlSpan = new URLSpan(urlStr);
            spannableString.setSpan(urlSpan, start, start + urlStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

}
