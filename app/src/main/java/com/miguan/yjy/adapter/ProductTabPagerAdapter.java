package com.miguan.yjy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.miguan.yjy.module.product.ProductComponentFragment;

import java.util.ArrayList;

/**
 * @作者 cjh
 * @日期 2017/3/29 11:38
 * @描述
 */

public class ProductTabPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> datas;

    public ProductTabPagerAdapter(ArrayList<String> datas, FragmentManager fm) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return new ProductComponentFragment();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position);
    }

}
