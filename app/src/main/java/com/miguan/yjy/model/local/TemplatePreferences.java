package com.miguan.yjy.model.local;

import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/6/2. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplatePreferences {

    private static final String PRE_NAME = "pre_template";

    private static final String KEY_FIRST_TEMPLATE_HOME = "first_template_home";

    private static final String KEY_FIRST_TEMPLATE_DETAIL = "first_template_detail";

    private static final String KEY_TEMPLATE ="template";

    public static boolean isFirstHome() {
        return LUtils.getPreferences(PRE_NAME).getBoolean(KEY_FIRST_TEMPLATE_HOME, true);
    }

    public static void setFirstHome(boolean isFirst) {
        LUtils.getPreferences(PRE_NAME).edit().putBoolean(KEY_FIRST_TEMPLATE_HOME, isFirst).apply();
    }

    public static boolean isFirstDetail() {
        return LUtils.getPreferences(PRE_NAME).getBoolean(KEY_FIRST_TEMPLATE_DETAIL, true);
    }

    public static void setFirstDetail(boolean isFirst) {
        LUtils.getPreferences(PRE_NAME).edit().putBoolean(KEY_FIRST_TEMPLATE_DETAIL, isFirst).apply();
    }

    public static void setTemplate(String template) {
        LUtils.getPreferences(PRE_NAME).edit().putString(KEY_TEMPLATE, template).apply();
    }

    public static String getTemplate() {
        return LUtils.getPreferences(PRE_NAME).getString(KEY_TEMPLATE, "");
    }

}
