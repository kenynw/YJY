package com.miguan.yjy.module.article;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.ArticleCate;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.LoadingImageView;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/15. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateArticleViewHolder extends EvaluateCommendVH {

    @BindView(R.id.rl_article_cate)
    RelativeLayout mRlArticleCate;

    @BindView(R.id.recy_article_cate)
    RecyclerView mRecyArticle;

    @BindView(R.id.tv_evaluate_label)
    TextView mTvLabel;

    @BindView(R.id.dv_evaluate_product_img)
    SimpleDraweeView mDvProductImg;

    @BindView(R.id.tv_evaluate_product_name)
    TextView mTvProductName;

    @BindView(R.id.tv_evaluate_product_rating)
    RatingBar mTvProductRating;

    @BindView(R.id.tv_evaluate_product_spec)
    TextView mTvProductSpec;

    @BindView(R.id.ll_evaluate_product)
    LinearLayout mLlProduct;

    @BindView(R.id.tv_evaluate_commend_like)
    TextView mTvCommendLike;

    @BindView(R.id.tv_evaluate_commend_comment)
    TextView mTvCommendComment;

    private OnLoadDataListener mListener;

    private CateAdapter mCateAdapter;

    public EvaluateArticleViewHolder(ViewGroup parent, OnLoadDataListener listener) {
        super(parent, R.layout.item_list_article_cate);
        ButterKnife.bind(this, itemView);
        mListener = listener;
        mRecyArticle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyArticle.addItemDecoration(new DividerDecoration(android.R.color.transparent, LUtils.dp2px(10)));
        mRecyArticle.setAdapter(mCateAdapter = new CateAdapter());
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
        if (data.getComment() == null) return;
        mCateAdapter.addAll(mListener.getCates());
        mRlArticleCate.setOnClickListener(v -> ArticleListActivityPresenter.start(getContext(), mListener.getCates(), 0));
    }

    public interface OnLoadDataListener {
        ArrayList<ArticleCate> getCates();
    }

    private class CateAdapter extends RecyclerView.Adapter<ArticleCateViewHolder> {

        private ArrayList<ArticleCate> mCates;

        public CateAdapter() {
            mCates = new ArrayList<>();
        }

        @Override
        public ArticleCateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LoadingImageView iv = new LoadingImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(new ViewGroup.LayoutParams(LUtils.dp2px(195), LUtils.dp2px(111)));
            return new ArticleCateViewHolder(iv);
        }

        @Override
        public void onBindViewHolder(ArticleCateViewHolder holder, int position) {
            if (mCates != null && mCates.size() > 0) {
                Glide.with(getContext())
                        .load(mCates.get(position).getCate_img())
                        .placeholder(R.mipmap.def_image_loading)
                        .error(R.mipmap.def_image_loading)
                        .into((ImageView) holder.itemView);
                holder.itemView.setOnClickListener(v ->
                        ArticleListActivityPresenter.start(getContext(), mCates, position)
                );
            }
        }

        @Override
        public int getItemCount() {
            return mCates != null && mCates.size() > 0 ? mCates.size() : 5;
        }

        public void addAll(Collection<ArticleCate> cates) {
            mCates.clear();
            mCates.addAll(cates);
            notifyItemRangeChanged(0, cates.size());
        }

    }

    private class ArticleCateViewHolder extends RecyclerView.ViewHolder {

        public ArticleCateViewHolder(View itemView) {
            super(itemView);
        }

    }

}
