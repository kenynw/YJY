package com.miguan.yjy.module.main;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Article;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ArticleDetailPresenter.class)
public class ArticleDetailActivity extends BaseDataActivity<ArticleDetailPresenter, Article> {

    @BindView(R.id.fl_article_detail_star)
    FrameLayout mFlStar;

    @BindView(R.id.fl_article_detail_comment)
    FrameLayout mFlComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_article_detail);
        ButterKnife.bind(this);

        mFlStar.setOnClickListener(v -> getPresenter().star());
        mFlComment.setOnClickListener(v -> ArticleCommentPresenter.start(this, 0));
    }

    public void setData(Article article) {

    }

}
