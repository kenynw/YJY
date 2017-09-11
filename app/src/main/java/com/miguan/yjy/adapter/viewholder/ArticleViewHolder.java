package com.miguan.yjy.adapter.viewholder;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.module.article.ArticleDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleViewHolder extends BaseViewHolder<Article> {

    @BindView(R.id.dv_article_thumb)
    ImageView mDvThumb;

    @BindView(R.id.tv_article_title)
    TextView mTvTitle;

    @BindView(R.id.tv_article_date)
    TextView mTvDate;

    @BindView(R.id.tv_article_comment)
    TextView mTvComment;

    public ArticleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_article);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Article data) {
        if (data.getId() == 0) {
            mDvThumb.setVisibility(View.GONE);
            mTvTitle.setText("该文章已被删除~");
        } else {
            mDvThumb.setImageURI(Uri.parse(data.getArticle_img()));
            mTvTitle.setText(data.getTitle());
            mTvDate.setText(data.getCreated_at());
            mTvComment.setText(String.valueOf(data.getClick_num()));
            itemView.setOnClickListener(v -> ArticleDetailPresenter.start(getContext(), data.getId()));
        }
    }

}
