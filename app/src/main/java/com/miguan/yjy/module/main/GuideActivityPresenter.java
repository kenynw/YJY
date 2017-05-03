package com.miguan.yjy.module.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017/4/21. LiaoPeiKun Inc. All rights reserved.
 */

public class GuideActivityPresenter extends Presenter<GuideActivity> {

    private GuideAdapter mGuideAdapter;

    public GuideAdapter getAdapter() {
        if (mGuideAdapter == null) {
            mGuideAdapter = new GuideAdapter(getView());
        }
        return mGuideAdapter;
    }

    private class GuideAdapter extends PagerAdapter {

        private Context mContext;

        private List<View> mViews;

        private int[] mResList = new int[] {
                R.mipmap.image_guide_0,
                R.mipmap.image_guide_1,
                R.mipmap.image_guide_2,
                R.mipmap.image_guide_3
        };

        public GuideAdapter(Context context) {
            mContext = context;
            mViews = new ArrayList<>();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setImageResource(mResList[position]);

            if (position == mResList.length - 1) {
                iv.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                    LUtils.getPreferences().edit().putBoolean("is_first", false).apply();
                    getView().finish();
                });
            }

            mViews.add(iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public int getCount() {
            return mResList.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
