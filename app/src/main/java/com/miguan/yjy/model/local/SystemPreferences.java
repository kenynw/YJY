package com.miguan.yjy.model.local;

import android.content.Context;

import com.miguan.yjy.utils.LUtils;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/4/11 9:55
 * @描述
 */

public class SystemPreferences {

    public static final String SEARCH_NAME = "searchName";

    public static void setSearchName(String name) {
        setString(SEARCH_NAME, name);
    }

    public static String getSearchName() {
        return getString(SEARCH_NAME);
    }

    public static void setString(String key, String value) {

        LUtils.getPreferences().edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return LUtils.getPreferences().getString(key, "");
    }


    /**
     * 存储List<String>
     *
     * @param context
     * @param key
     *            List<String>对应的key
     * @param strList
     *            对应需要存储的List<String>
     */
    public static void putStrListValue(Context context, String key,
                                       List<String> strList) {
        if (null == strList) {
            return;
        }
        // 保存之前先清理已经存在的数据，保证数据的唯一性
//        removeStrList(context, key);
//        int size = strList.size();
//        putIntValue(context, key + "size", size);
//        for (int i = 0; i < size; i++) {
//            putStringValue(context, key + i, strList.get(i));
//        }
    }





}
