package com.miguan.yjy.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.miguan.yjy.module.product.AllProductFragment;
import com.miguan.yjy.module.product.ArticleListFragment;
import com.miguan.yjy.module.product.StarProductFragment;

/**
 * @作者 cjh
 * @日期 2017/5/19 15:22
 * @描述
 */

public class BrandPagerAdapter extends FragmentPagerAdapter {

    public static final String EXTRA_BRAND_ID = "brandId";

    int type = 1;
    long brandId;

    public BrandPagerAdapter(FragmentManager fm, int type, long brandId) {
        super(fm);
        this.type = type;
        this.brandId = brandId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StarProductFragment starProductFragment = new StarProductFragment();
                Bundle bundle = new Bundle();
                bundle.putLong(EXTRA_BRAND_ID, brandId);
                starProductFragment.setArguments(bundle);
                return starProductFragment;

            case 1:
                AllProductFragment allProductFragment = new AllProductFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putLong(EXTRA_BRAND_ID, brandId);
                allProductFragment.setArguments(bundle1);
                return allProductFragment;
            case 2:
                ArticleListFragment articleListFragment = new ArticleListFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putLong(EXTRA_BRAND_ID, brandId);
                articleListFragment.setArguments(bundle2);
                return articleListFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return type == 1 ? 3 : 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "明星产品";

            case 1:
                return "所有产品";
            case 2:

                return "文章列表";
        }
        return super.getPageTitle(position);

    }


}
