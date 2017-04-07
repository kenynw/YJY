package com.miguan.yjy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class BannerPagerAdapter extends PagerAdapter {

    private Context mContext;

    private List<Banner> mBannerList;

    private List<View> mViewList;

    public BannerPagerAdapter(Context context, List<Banner> banners) {
        this.mContext = context;
        this.mBannerList = banners;

        mViewList = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(mContext);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(lp);

        Glide.with(mContext)
                .load("http://oss.yjyapp.com/cs/static/h5/images/banner.jpg")
                .placeholder(R.mipmap.def_image_loading)
                .error(R.mipmap.def_image_loading)
                .centerCrop()
                .into(iv);

        container.addView(iv);
        mViewList.add(iv);
        return iv;
    }

    @Override
    public int getCount() {
        return mBannerList.size() + 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}
