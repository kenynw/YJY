package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.BaseFragmentPagerAdapter;
import com.miguan.yjy.module.ask.MyAskFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/8/25. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(MyAskPresenter.class)
public class MyAskActivity extends ChainBaseActivity<MyAskPresenter> {

    @BindView(R.id.rb_my_ask_question)
    RadioButton mRbQuestion;

    @BindView(R.id.rb_my_ask_answer)
    RadioButton mRbAnswer;

    @BindView(R.id.rg_my_ask_tab)
    RadioGroup mRgTab;

    @BindView(R.id.vp_my_ask)
    ViewPager mVpMyAsk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_ask);
        setToolbarTitle(R.string.btn_me_ask);
        ButterKnife.bind(this);

        List<Fragment> list = new ArrayList<>();
        list.add(MyAskFragment.get(MyAskFragment.TYPE_QUESTION));
        list.add(MyAskFragment.get(MyAskFragment.TYPE_ANSWER));
        mVpMyAsk.setAdapter(new BaseFragmentPagerAdapter(this, list, getSupportFragmentManager()));
        mVpMyAsk.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                (position == 0 ? mRbQuestion : mRbAnswer).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRgTab.setOnCheckedChangeListener((group, checkedId) -> {
            int index = (checkedId == R.id.rb_my_ask_question ? 0 : 1);
            mVpMyAsk.setCurrentItem(index);
        });
    }

}
