package com.miguan.yjy.model.local;


import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/2/23. LiaoPeiKun Inc. All rights reserved.
 */

public class UserPreferences {

    private static final String KEY_USER_ID = "user_id";

    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_BIRTHDAY = "userBirthday";
    private static final String KEY_USER_SEX = "userSex";

    public static int getUserID() {
        return LUtils.getPreferences().getInt(KEY_USER_ID, 0);
    }

    public static String getUsername() {
        return getString(KEY_USERNAME);
    }

    public static void setUserID(int value) {
        LUtils.getPreferences().edit().putInt(KEY_USER_ID, value).apply();
    }

    public static void setUserBirthday(String value) {
        setString(KEY_USER_BIRTHDAY, value);
    }

    public static String getUserBirthday() {
        return getString(KEY_USER_BIRTHDAY);
    }
    public static void setUserSex(String value) {
        setString(KEY_USER_SEX, value);
    }

    public static String getUserSex() {
        return getString(KEY_USER_SEX);
    }

    public static void setUsername(String value) {
        setString(KEY_USERNAME, value);
    }


    private static String getString(String key) {
        return LUtils.getPreferences().getString(key, "").trim();
    }

    private static void setString(String key, String value) {
        LUtils.getPreferences().edit().putString(key, value).apply();
    }

}
