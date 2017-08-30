package com.miguan.yjy.module.ask;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Ask;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Copyright (c) 2017/8/24. LiaoPeiKun Inc. All rights reserved.
 */
public class AskViewHolder extends BaseViewHolder<Ask> {

    @BindView(R.id.dv_ask_product_image)
    SimpleDraweeView mDvProductImage;

    @BindView(R.id.tv_ask_title)
    TextView mTvTitle;

    @BindView(R.id.tv_ask_desc)
    TextView mTvDesc;

    @BindView(R.id.iv_ask_like_icon)
    ImageView mIvLikeIcon;

    @BindView(R.id.tv_ask_like)
    TextView mTvLike;

    @BindView(R.id.ll_ask_like)
    LinearLayout mLlLike;

    public AskViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_ask);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Ask data) {
        mDvProductImage.setImageURI(data.getProduct_img());
        mTvTitle.setText(data.getSubject());
        if (!TextUtils.isEmpty(data.getReply())) {
            mLlLike.setVisibility(View.VISIBLE);
            mLlLike.setOnClickListener(v -> {
                ProductModel.getInstance().addAskLike(data.getAskReplyId()).unsafeSubscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        int curCount = TextUtils.isDigitsOnly(mTvLike.getText())
                                ? Integer.valueOf(mTvLike.getText().toString()) : 0;
                        int result = curCount + (data.getIs_like() == 1 ? -1 : 1);
                        data.setIs_like(data.getIs_like() == 1 ? 2 : 1);
                        data.setLike_num(result);
                        setLike(data);
                    }
                });
            });

            mTvDesc.setText(data.getReply());
        } else {
            mLlLike.setVisibility(View.INVISIBLE);
            mTvDesc.setText("暂无回答");
        }
        setLike(data);
        itemView.setOnClickListener(v -> AskDetailActivityPresenter.start(getContext(),
                data.getProduct_id(), data.getAskid()));
    }

    private void setLike(Ask data) {
        mTvLike.setText(data.getLike_num() > 0 ? data.getLike_num() + "" : "点赞");
        mIvLikeIcon.setImageResource(data.getIs_like() == 1 ? R.mipmap.ic_like_pressed : R.mipmap.ic_like_normal);
    }

}
