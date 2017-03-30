package com.miguan.yjy.model.local;


import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/2/23. LiaoPeiKun Inc. All rights reserved.
 */

public class UserPreferences {

    private static final String KEY_USER_ID = "user_id";

    private static final String KEY_TOKEN ="token";

    private static final String KEY_USER_NIM_TOKEN = "auth_key";

    public static int getUserID() {
        return LUtils.getPreferences().getInt(KEY_USER_ID, 0);
    }

    public static String getToken() {
        return getString(KEY_TOKEN);
    }

    public static String getNIMToken() {
        return getString(KEY_USER_NIM_TOKEN);
    }

    public static void setUserID(int value) {
        LUtils.getPreferences().edit().putInt(KEY_USER_ID, value).apply();
    }

    public static void setToken(String value) {
        setString(KEY_TOKEN, value);
    }

    public static void setNIMToken(String value) {
        setString(KEY_USER_NIM_TOKEN, value);
    }

    private static String getString(String key) {
        return LUtils.getPreferences().getString(key, "").trim();
    }

    private static void setString(String key, String value) {
        LUtils.getPreferences().edit().putString(key, value).apply();
    }

}
