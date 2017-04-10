package com.miguan.yjy.adapter.viewholder;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
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

    @BindView(R.id.dv_star_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_star_title)
    TextView mTvTitle;

    public StarViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_star);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Article data) {
        mDvThumb.setImageURI(Uri.parse(data.getArticle_img()));
        mTvTitle.setText(data.getTitle());
        itemView.setOnClickListener(v -> ArticleDetailPresenter.start(getContext(), data));
    }

}
