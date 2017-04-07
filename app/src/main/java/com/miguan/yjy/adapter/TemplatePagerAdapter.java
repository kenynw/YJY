package com.miguan.yjy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.miguan.yjy.model.constant.TemplatePager;
import com.miguan.yjy.module.template.GenTemplateActivity;

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

        for (TemplatePager templatePager : TemplatePager.values()) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setImageResource(templatePager.mImageRes);
            mViews.add(iv);
        }

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        view.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, GenTemplateActivity.class)));
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return TemplatePager.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
