package com.miguan.yjy.model.local;


import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/2/23. LiaoPeiKun Inc. All rights reserved.
 */

public class PushPreferences {

    private static final String KEY_ALL = "push_all";

    private static final String KEY_MATCH ="push_match";

    public static boolean isPushAll() {
        return getBoolean(KEY_ALL);
    }

    public static boolean isPushMatch() {
        return getBoolean(KEY_MATCH);
    }

    public static void setPushAll(boolean value) {
        setBoolean(KEY_ALL, value);
    }

    public static void setPushMatch(boolean value) {
        setBoolean(KEY_MATCH, value);
    }

    private static boolean getBoolean(String key) {
        return LUtils.getPreferences().getBoolean(key, true);
    }

    private static void setBoolean(String key, boolean value) {
        LUtils.getPreferences().edit().putBoolean(key, value).apply();
    }

}
