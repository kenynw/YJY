package com.miguan.yjy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.miguan.yjy.module.template.GenTemplatePresenter;
import com.miguan.yjy.module.template.Template;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplatePagerAdapter extends PagerAdapter {

    private Context mContext;

    private List<View> mViews;

    public TemplatePagerAdapter(Context context) {
        mContext = context;
        mViews = new ArrayList<>();

        for (Template templatePager : Template.values()) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            iv.setImageResource(templatePager.mImageRes);
            mViews.add(iv);
        }

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        view.setOnClickListener(v -> GenTemplatePresenter.start(mContext, Template.values()[position]));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public int getCount() {
        return Template.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
