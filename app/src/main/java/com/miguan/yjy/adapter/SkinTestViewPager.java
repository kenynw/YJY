package com.miguan.yjy.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.module.test.SkinReadFragment;
import com.miguan.yjy.module.test.SkinRecomFragment;

/**
 * @作者 cjh
 * @日期 2017/6/16 15:35
 * @描述
 */

public class SkinTestViewPager extends FragmentStatePagerAdapter {

    private Test mTest;
    public static final String BUNDLE_TEST = "test";

    public SkinTestViewPager(FragmentManager fm, Test test) {
        super(fm);
        this.mTest = test;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                SkinReadFragment skinReadFragment = new SkinReadFragment();
                bundle.putParcelable(BUNDLE_TEST, mTest);
                skinReadFragment.setArguments(bundle);
                return skinReadFragment;
            case 1:
                Bundle bundle1 = new Bundle();
                SkinRecomFragment skinRecomFragment = new SkinRecomFragment();
                bundle1.putParcelable(BUNDLE_TEST, mTest);
                skinRecomFragment.setArguments(bundle1);
                return skinRecomFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "肤质解读";
            case 1:
                return "推荐产品";
        }
        return super.getPageTitle(position);
    }
}
