package com.miguan.yjy.model.local;


import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/2/23. LiaoPeiKun Inc. All rights reserved.
 */

public class UserPreferences {

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_AVATAR = "avatar";
    private static final String KEY_USER_BIRTHDAY = "user_birthday";
    private static final String KEY_USER_SEX = "user_sex";
    private static final String KEY_UNREAD_MSG = "unread_msg"; //未读消息
    private static final String KEY_OVERDUE_NUM = "overdue_num"; //过期产品数

    public static int getUserID() {
        return LUtils.getPreferences().getInt(KEY_USER_ID, 0);
    }

    public static void setUserID(int value) {
        LUtils.getPreferences().edit().putInt(KEY_USER_ID, value).apply();
    }

    public static String getUsername() {
        return getString(KEY_USERNAME);
    }

    public static void setUsername(String value) {
        setString(KEY_USERNAME, value);
    }

    public static String getAvatar() {
        return getString(KEY_AVATAR);
    }

    public static void setAvatar(String value) {
        setString(KEY_AVATAR, value);
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

    public static int getUnreadMsg() {
        return LUtils.getPreferences().getInt(KEY_UNREAD_MSG, 0);
    }

    public static void setUnreadMsg(int value) {
        LUtils.getPreferences().edit().putInt(KEY_UNREAD_MSG, value).apply();
    }

    public static int getOverdueNum() {
        return LUtils.getPreferences().getInt(KEY_OVERDUE_NUM, 0);
    }

    public static void setOverdueNum(int value) {
        LUtils.getPreferences().edit().putInt(KEY_OVERDUE_NUM, value).apply();
    }

    private static String getString(String key) {
        return LUtils.getPreferences().getString(key, "").trim();
    }

    private static void setString(String key, String value) {
        LUtils.getPreferences().edit().putString(key, value).apply();
    }

}
