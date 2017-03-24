package com.miguan.yjy.module.main;

import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ArticleCommentPresenter.class)
public class ArticleCommentActivity extends ChainBaseActivity<ArticleCommentPresenter> {

    @BindView(R.id.et_article_comment_content)
    EditText mEtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_article_comment);
        setToolbarTitle(R.string.text_add_comment);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
