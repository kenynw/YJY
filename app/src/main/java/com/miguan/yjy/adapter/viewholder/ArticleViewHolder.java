package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.module.main.ArticleDetailPresenter;
import com.miguan.yjy.utils.GlideRoundTransform;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleViewHolder extends BaseViewHolder<Article> {

    @BindView(R.id.iv_article_thumb)
    ImageView mIvThumb;

    @BindView(R.id.tv_article_title)
    TextView mTvTitle;

    @BindView(R.id.tv_article_date)
    TextView mTvDate;

    @BindView(R.id.tv_article_like)
    TextView mTvLike;

    public ArticleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_article);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Article data) {
        Glide.with(getContext())
                .load("http://oss.yjyapp.com/uploads/" + data.getArticle_img())
                .placeholder(R.mipmap.def_image_loading)
                .error(R.mipmap.def_image_loading)
                .transform(new GlideRoundTransform(getContext(), 8))
                .into(mIvThumb);

        mTvTitle.setText(data.getTitle());
        mTvDate.setText(data.getCreated_at());
        mTvLike.setText(String.valueOf(data.getLike_num()));
        itemView.setOnClickListener(v -> ArticleDetailPresenter.start(getContext(), data));
    }
}
