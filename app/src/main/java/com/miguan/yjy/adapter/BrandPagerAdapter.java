package com.miguan.yjy.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dsk.chain.expansion.list.BaseListFragment;
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

    int isStart = 1;//是否显示明星产品
    int type = 1;//是否显示文章列表
    long brandId;
    String[] titileName = {"明星产品","所有产品","文章列表"};

    public BrandPagerAdapter(FragmentManager fm, int type, int isStart, long brandId) {
        super(fm);
        this.type = type;
        this.isStart = isStart;
        this.brandId = brandId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (isStart == 0) {
                    return showAllProduct();
                } else {
                    return showStarProduct();
                }
            case 1:
                if (isStart == 0) {
                    return showarticleList();
                } else {
                    return showAllProduct();
                }
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

        if (isStart == 1 && type == 1) {
            return 3;
        } else if (isStart == 0 && type == 0) {
            return 1;
        } else {
            return 2;
        }

    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                if (isStart == 0) {
                    return titileName[1];
                } else {
                    return titileName[0];
                }
            case 1:
                if (isStart == 0) {
                    return titileName[2];
                } else {
                    return titileName[1];
                }
            case 2:
                return titileName[2];
        }
        return super.getPageTitle(position);

    }

    private BaseListFragment showStarProduct() {
        StarProductFragment starProductFragment = new StarProductFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_BRAND_ID, brandId);
        starProductFragment.setArguments(bundle);
        return starProductFragment;
    }

    private BaseListFragment showAllProduct() {
        AllProductFragment allProductFragment = new AllProductFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putLong(EXTRA_BRAND_ID, brandId);
        allProductFragment.setArguments(bundle1);
        return allProductFragment;
    }

    private BaseListFragment showarticleList() {
        ArticleListFragment articleListFragment = new ArticleListFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putLong(EXTRA_BRAND_ID, brandId);
        articleListFragment.setArguments(bundle2);
        return articleListFragment;
    }


}
