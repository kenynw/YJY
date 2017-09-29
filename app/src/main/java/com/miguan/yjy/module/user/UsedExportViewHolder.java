package com.miguan.yjy.module.user;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.UserProduct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/9/27. LiaoPeiKun Inc. All rights reserved.
 */

public class UsedExportViewHolder extends BaseViewHolder<UserProduct> {

    @BindView(R.id.dv_product_used_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_product_used_name)
    TextView mTvName;

    @BindView(R.id.tv_product_used_residue)
    TextView mTvResidue;

    public UsedExportViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_used_export);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(UserProduct data) {
        mDvThumb.setImageURI(Uri.parse(data.getImg()));
        String title = data.getProduct().contains(data.getBrand_name()) ? data.getProduct()
                : data.getBrand_name() + data.getProduct();
        mTvName.setText(title);

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
        long overdueTime = (data.getIs_seal() == 0 ? data.getOverdue_time() :
                Math.min(calendar.getTime().getTime() / 1000, data.getOverdue_time()))
                + (24 * 60 * 60 - 1);
        int restDay = overdueTime > System.currentTimeMillis() / 1000 ?
                (int) ((overdueTime - System.currentTimeMillis() / 1000) / 3600 / 24) + 1 : 0;
        mTvResidue.setText(restDay + "天");
    }

}
