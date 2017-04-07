package com.miguan.yjy.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.module.test.TestTabFragment;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/4/6 11:50
 * @描述
 */

public class TestTabPagerAdapter extends FragmentPagerAdapter{

    Context mContext;
    int type;
    List<Test> mTests;
    public TestTabPagerAdapter(FragmentManager fm, Context context,int type, List<Test>list) {
        super(fm);
        mContext = context;
        this.type = type;
        mTests = list;
    }

    @Override
    public Fragment getItem(int position) {
        return new TestTabFragment();
    }

    @Override
    public int getCount() {
        return mTests.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTests.get(position).getTestName();
    }
}
