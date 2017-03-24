package com.miguan.yjy.module.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.EvaluateViewHolder;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ArticleDetailPresenter.class)
public class ArticleDetailActivity extends BaseListActivity<ArticleDetailPresenter> {

    @BindView(R.id.fl_article_detail_star)
    FrameLayout mFlStar;

    @BindView(R.id.fl_article_detail_comment)
    FrameLayout mFlComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarTitle("这是品牌名");
        mFlStar.setOnClickListener(v -> getPresenter().star());
        mFlComment.setOnClickListener(v -> ArticleCommentPresenter.start(this, 0));
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setContainerLayoutRes(R.layout.main_activity_article_detail).setRefreshAble(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LUtils.toast("分享中...");
        return super.onOptionsItemSelected(item);
    }
}
