package com.miguan.yjy.module.user;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.module.main.ArticleDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class StarViewHolder extends BaseViewHolder<Article> {

    @BindView(R.id.iv_star_thumb)
    ImageView mIvThumb;

    @BindView(R.id.tv_star_title)
    TextView mTvTitle;

    public StarViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_star);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Article data) {
        Glide.with(getContext())
                .load(data.getArticle_img())
                .centerCrop()
                .placeholder(R.mipmap.def_image_loading)
                .error(R.mipmap.def_image_loading)
                .into(mIvThumb);
        mTvTitle.setText(data.getTitle());
        itemView.setOnClickListener(v -> ArticleDetailPresenter.start(getContext(), data));
    }

}
