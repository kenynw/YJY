package com.miguan.yjy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Banner;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.module.main.ArticleDetailPresenter;
import com.miguan.yjy.module.product.ProductDetailPresenter;

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
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        Banner banner = mBannerList.get(position);
        Glide.with(mContext)
                .load(banner.getImg())
                .placeholder(R.mipmap.def_image_loading)
                .error(R.mipmap.def_image_loading)
                .centerCrop()
                .into(iv);

        iv.setOnClickListener(v -> {
            if (banner.getType() == 1) {
                WebViewActivity.start(mContext, "", banner.getUrl());
            } else if (banner.getType() == 2) {
                ProductDetailPresenter.start(mContext, banner.getRelation_id());
            } else if (banner.getType() == 3) {
                ArticleDetailPresenter.start(mContext, banner.getRelation_id());
            }
        });

        container.addView(iv);
        mViewList.add(iv);
        return iv;
    }

    @Override
    public int getCount() {
        return mBannerList.size();
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
