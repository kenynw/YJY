package com.miguan.yjy.module.template;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.TemplatePagerAdapter;
import com.miguan.yjy.model.local.TemplatePreferences;
import com.miguan.yjy.widget.CirclePageIndicator;
import com.miguan.yjy.widget.TemplateTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(TemplatesPresenter.class)
public class TemplatesActivity extends ChainBaseActivity<TemplatesPresenter> implements View.OnTouchListener {

    @BindView(R.id.container_template)
    LinearLayout mContainer;

    @BindView(R.id.vp_template)
    ViewPager mVp;

    @BindView(R.id.cpi_template)
    CirclePageIndicator mCpi;

    @BindView(R.id.iv_template_gen_guide)
    ImageView mIvGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_template);
        setToolbarTitle(R.string.text_template_title);
        ButterKnife.bind(this);

        mContainer.setOnTouchListener(this);
        mVp.setOffscreenPageLimit(3);
        mVp.setPageMargin(-50);
        mVp.setPageTransformer(true, new TemplateTransformer());
        mVp.setAdapter(new TemplatePagerAdapter(this));
        mCpi.setViewPager(mVp);
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

}
