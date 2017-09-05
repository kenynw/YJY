package com.miguan.yjy.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Copyright (c) 2017/9/5. LiaoPeiKun Inc. All rights reserved.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private List<Fragment> mFragments;

    public BaseFragmentPagerAdapter(Context context, List<Fragment> fragments, FragmentManager fm) {
        super(fm);
        mContext = context;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
