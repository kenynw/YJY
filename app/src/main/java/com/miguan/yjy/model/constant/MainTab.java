package com.miguan.yjy.model.constant;

import com.miguan.yjy.R;
import com.miguan.yjy.module.main.HomeFragment;

/**
 * Copyright (c) 2017/3/17. LiaoPeiKun Inc. All rights reserved.
 */

public enum MainTab {

    HOME(0, R.mipmap.ic_launcher, R.string.app_name, HomeFragment.class),
    MODULE(1, R.mipmap.ic_launcher, R.string.btn_main_module, HomeFragment.class),
    TEST(2, R.mipmap.ic_launcher, R.string.btn_main_test, HomeFragment.class),
    ME(3, R.mipmap.ic_launcher, R.string.btn_main_me, HomeFragment.class);

    public final int mTabIndex;

    public final int mIconRes;

    public final int mStrRes;

    public final Class mFragment;

    MainTab(int tabIndex, int iconRes, int strRes, Class fragment) {
        mTabIndex = tabIndex;
        mIconRes = iconRes;
        mStrRes = strRes;
        mFragment = fragment;
    }

    public static MainTab tabFromIndex(int index) {
        for (MainTab mainTab : MainTab.values()) {
            if (mainTab.mTabIndex == index) return mainTab;
        }
        return null;
    }

}
