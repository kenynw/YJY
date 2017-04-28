package com.miguan.yjy.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.ComponentTag;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/4/6 11:50
 * @描述
 */

public class ProductReadTabPagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    int type;
    ArrayList<Component> mTests;
    List<ComponentTag> mComponentTags;

    public ProductReadTabPagerAdapter(FragmentManager fm, Context context, int type, ArrayList<Component> list, List<ComponentTag> mComponentTags) {
        super(fm);
        mContext = context;
        this.type = type;
        mTests = list;
        this.mComponentTags = mComponentTags;
    }

    @Override
    public Fragment getItem(int position) {
//        return ProductReadTabFragmentPrensenter.getInstance(mTests);
        return null;
    }

    @Override
    public int getCount() {
        return mComponentTags.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mComponentTags.get(position).getName();
    }

    public void setTests(ArrayList<Component> tests) {
        mTests = tests;
    }

    public void setComponentTags(List<ComponentTag> componentTags) {
        mComponentTags = componentTags;
    }
}
