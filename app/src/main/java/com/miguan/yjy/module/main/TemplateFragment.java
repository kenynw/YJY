package com.miguan.yjy.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dsk.chain.bijection.ChainFragment;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.TemplatePagerAdapter;
import com.miguan.yjy.model.local.TemplatePreferences;
import com.miguan.yjy.widget.CirclePageIndicator;
import com.miguan.yjy.widget.TemplateTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(TemplatePresenter.class)
public class TemplateFragment extends ChainFragment<TemplatePresenter> implements View.OnTouchListener {

    @BindView(R.id.container_template)
    LinearLayout mContainer;

    @BindView(R.id.vp_template)
    ViewPager mVp;

    @BindView(R.id.cpi_template)
    CirclePageIndicator mCpi;

    @BindView(R.id.iv_template_gen_guide)
    ImageView mIvGuide;

    private Unbinder mBind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_fragment_template, container, false);
        mBind = ButterKnife.bind(this, view);

        mContainer.setOnTouchListener(this);
        mVp.setOffscreenPageLimit(3);
        mVp.setPageMargin(-50);
        mVp.setPageTransformer(true, new TemplateTransformer());
        mVp.setAdapter(new TemplatePagerAdapter(getActivity()));
        mCpi.setViewPager(mVp);

        return view;
    }

    public void showGuide() {
        mIvGuide.setVisibility(View.VISIBLE);
        mIvGuide.setOnClickListener(v -> {
            mIvGuide.setVisibility(View.GONE);
            TemplatePreferences.setFirstHome(false);
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mVp.dispatchTouchEvent(event);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBind.unbind();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
