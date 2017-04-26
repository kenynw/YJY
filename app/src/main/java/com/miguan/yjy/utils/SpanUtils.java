package com.miguan.yjy.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.miguan.yjy.module.common.WebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者 cjh
 * @日期 2017/3/27 14:24
 * @描述 SpanUtils
 */

public class SpanUtils {

    /**
     * 网址要被替换成的文字
     */
    public static String REPLACEMENT_STRING = "网页链接";

    /**
     * 匹配网址的正则表达式。有http://、https://、ftp://这3中开头的
     */
    public static String urlRegex = "((http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>,]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>,]*)?)|([a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>,]*)?)";


    /**
     * @param text
     * @param color
     */
    public static SpannableStringBuilder addColor(CharSequence text, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        if (color != 0) {
            builder.setSpan(new ForegroundColorSpan(color), 0, text.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public static SpannableString getContentSpannable(String content, TextView textView) {
        Matcher matcher = Pattern.compile(urlRegex).matcher(content);
        List<String> urlList = new ArrayList<>();
        while (matcher.find()) {
            String urlStr = matcher.group();
            urlList.add(urlStr);
            content = content.replace(urlStr, REPLACEMENT_STRING);

            LUtils.log("url: " + urlStr);
        }

        final SpannableString spannableString = new SpannableString(content);
        if (urlList.size() > 0) {
            int urlStartNew = 0;
            int urlStartOld = 0;

            String urlTemp = content;

            for (String url : urlList) {
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        WebViewActivity.start(textView.getContext(), "", url);
                    }
                }, urlStartOld + urlTemp.indexOf(REPLACEMENT_STRING),
                        urlStartOld + urlTemp.indexOf(REPLACEMENT_STRING) + REPLACEMENT_STRING.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(spannableString);
                textView.setMovementMethod(LinkMovementMethod.getInstance());

                urlStartNew = urlTemp.indexOf(REPLACEMENT_STRING) + REPLACEMENT_STRING.length();
                urlStartOld += urlStartNew;
                urlTemp = urlTemp.substring(urlStartNew);
            }
        }

        return spannableString;
    }

}
