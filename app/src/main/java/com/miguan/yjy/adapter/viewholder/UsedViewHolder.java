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
import com.miguan.yjy.widget.SuperTextView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    SuperTextView mTvIsSeal;

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
        mTvName.setText(data.getBrand_name() + data.getProduct());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = null;
        try {
            date = format.parse(data.getSeal_time());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, data.getQuality_time());
        long overdueTime = Math.min(calendar.getTime().getTime() / 1000, data.getOverdue_time());
        int restDay = overdueTime * 1000 > System.currentTimeMillis() ?
                (int) ((overdueTime - System.currentTimeMillis() / 1000) / 3600 / 24) + 1 : 0;

        ForegroundColorSpan colorSpan;
        if (restDay <= 60) {
            colorSpan = new ForegroundColorSpan(0xFFFF5555);
        } else {
            colorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorPrimary));
        }
        SpannableString spann = new SpannableString(restDay <= 0 ? "已过期" : "剩余 " + restDay + " 天");
        if (restDay <= 0) {
            spann.setSpan(colorSpan, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spann.setSpan(colorSpan, 3, (restDay + "").length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mTvResidue.setText(spann);

        mTvIsSeal.setText(data.getIs_seal() == 0 ? "未开封" : "已开封");
        mTvIsSeal.setTextColor(data.getIs_seal() == 0 ? 0xFFBCBCBC : 0xFFFF5555);
        mTvIsSeal.setStrokeColor(data.getIs_seal() == 0 ? 0xFFBCBCBC : 0xFFFF5555);

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
