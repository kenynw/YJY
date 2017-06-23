package com.miguan.yjy.module.article;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miguan.yjy.module.article.ArticleListActivityPresenter.EXTRA_CUR_POSITION;

/**
 * Copyright (c) 2017/6/15. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ArticleListActivityPresenter.class)
public class ArticleListActivity extends ChainBaseActivity<ArticleListActivityPresenter> {

    @BindView(R.id.tab_article_list)
    TabLayout mTab;

    @BindView(R.id.vp_article_list)
    ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artilce_activity_list);
        setToolbarTitle(R.string.text_hot_article);
        ButterKnife.bind(this);

        mVp.setAdapter(getPresenter().getAdapter());
        mTab.setupWithViewPager(mVp);
        mTab.getTabAt(getIntent().getIntExtra(EXTRA_CUR_POSITION, 0)).select();
    }

}
