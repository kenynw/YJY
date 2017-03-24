package com.miguan.yjy.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.miguan.yjy.model.constant.MainTab;

/**
 * Copyright (c) 2017/3/17. LiaoPeiKun Inc. All rights reserved.
 */

public class MainTabPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragments;

    private Context mContext;

    public MainTabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mFragments = new Fragment[MainTab.values().length];

        for (MainTab mainTab : MainTab.values()) {
            try {
                Fragment fragment = (Fragment) mainTab.mFragment.newInstance();
                mFragments[mainTab.mTabIndex] = fragment;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        MainTab mainTab = MainTab.tabFromIndex(position);

        int resId = mainTab == null ? 0 : mainTab.mStrRes;

        return resId > 0 ? mContext.getText(resId) : "";
    }

    public int getIconRes(int position) {
        MainTab mainTab = MainTab.tabFromIndex(position);

        return mainTab == null ? 0 : mainTab.mIconRes;
    }

}
