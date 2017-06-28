package com.miguan.yjy.module.ask;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.widget.SuperTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright (c) 2017/6/23. LiaoPeiKun Inc. All rights reserved.
 */

public class AskTitleViewHolder extends BaseViewHolder<Ask> {

    @BindView(R.id.tv_question_title)
    TextView mTvTitle;

    @BindView(R.id.btn_question_to_ask)
    SuperTextView mBtnToAsk;

    @BindView(R.id.iv_ask_background)
    ImageView mIvBackground;

    public AskTitleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_ask_title);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Ask data) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mIvBackground.getLayoutParams();
        switch (getAdapterPosition() % 3) {
            case 0:
                mIvBackground.setImageResource(R.mipmap.bg_ask_item_1);
                layoutParams.gravity = Gravity.END;
                break;
            case 1:
                mIvBackground.setImageResource(R.mipmap.bg_ask_item_2);
                layoutParams.gravity = Gravity.BOTTOM;
                break;
            case 2:
                mIvBackground.setImageResource(R.mipmap.bg_ask_item_3);
                layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
                break;
        }
        mIvBackground.setLayoutParams(layoutParams);

        mTvTitle.setText(data.getSubject());
    }

}
