package com.miguan.yjy.model.constant;

import com.miguan.yjy.R;
import com.miguan.yjy.module.main.DiscoverFragment;
import com.miguan.yjy.module.main.HomeFragment;
import com.miguan.yjy.module.main.MainProductFragment;
import com.miguan.yjy.module.main.MeFragment;

/**
 * Copyright (c) 2017/3/17. LiaoPeiKun Inc. All rights reserved.
 */

public enum MainTab {

    HOME(0, R.drawable.tab_main_home_selector, R.string.app_name, HomeFragment.class),
    MODULE(1, R.drawable.tab_main_product_selector, R.string.btn_main_product, MainProductFragment.class),
    TEST(2, R.drawable.tab_main_discover_selector, R.string.text_discover, DiscoverFragment.class),
    ME(3, R.drawable.tab_main_me_selector, R.string.btn_main_me, MeFragment.class);

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
