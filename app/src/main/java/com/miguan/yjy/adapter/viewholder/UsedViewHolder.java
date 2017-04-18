package com.miguan.yjy.adapter.viewholder;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.UserProduct;
import com.miguan.yjy.model.services.ServicesResponse;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class UsedViewHolder extends BaseViewHolder<UserProduct> {

    @BindView(R.id.tv_product_used_open)
    TextView mTvOpen;

    @BindView(R.id.tv_product_used_delete)
    TextView mTvDelete;

    @BindView(R.id.tv_product_used_is_seal)
    TextView mTvIsSeal;

    @BindView(R.id.tv_product_used_name)
    TextView mTvName;

    @BindView(R.id.tv_product_used_date)
    TextView mTvDate;

    @BindView(R.id.tv_product_used_residue)
    TextView mTvResidue;

    public UsedViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_product_used);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(UserProduct data) {
        mTvName.setText(data.getProduct());

        int restDay = data.getOverdue_time() * 1000 > System.currentTimeMillis() ?
                (int) ((data.getOverdue_time() - System.currentTimeMillis() / 1000) / 3600 / 24) : 0;
        ForegroundColorSpan colorSpan;
        if (restDay <= 60) {
            colorSpan = new ForegroundColorSpan(0xFFFF5555);
        } else {
            colorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorPrimary));
        }
        SpannableString spann = new SpannableString("剩余 " + restDay + " 天");
        spann.setSpan(colorSpan, 3, (restDay + "").length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvResidue.setText(spann);

        mTvIsSeal.setText(data.getIs_seal() == 0 ? "未开封" : "已开封");
        mTvIsSeal.setTextColor(data.getIs_seal() == 0 ? 0xFFBCBCBC : 0xFFFF5555);
        mTvIsSeal.setBackgroundResource(data.getIs_seal() == 0 ? R.drawable.bg_gray_stroke : R.drawable.bg_red_stroke);
        mTvOpen.setVisibility(data.getIs_seal() == 0 ? View.VISIBLE : View.GONE);
        mTvDate.setVisibility(data.getIs_seal() == 0 ? View.GONE : View.VISIBLE);
        mTvDate.setText(data.getSeal_time());

        ServicesResponse<String> response = new ServicesResponse<String>() {
            @Override
            public void onNext(String s) {
                EventBus.getDefault().post(data);
            }
        };
        mTvDelete.setOnClickListener(v -> UserModel.getInstance().deleteUsedProduct(data.getId(), 2).unsafeSubscribe(response));
        mTvOpen.setOnClickListener(v -> UserModel.getInstance().deleteUsedProduct(data.getId(), 1).unsafeSubscribe(response));
    }
}
