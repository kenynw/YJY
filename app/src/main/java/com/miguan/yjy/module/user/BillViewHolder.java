package com.miguan.yjy.module.user;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Bill;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.product.BillSimpleViewHolder;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/9/21. LiaoPeiKun Inc. All rights reserved.
 */

public class BillViewHolder extends BillSimpleViewHolder {

    @BindView(R.id.tv_bill_delete)
    TextView mTvDelete;

    @BindView(R.id.cl_bill_product)
    ConstraintLayout mClProduct;

    public BillViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_bill);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Bill data) {
        setBill(data);
        mClProduct.setOnClickListener(v -> BillDetailPresenter.start(getContext(), data.getId(), data.getTitle()));
        mTvDelete.setOnClickListener(v ->
                new AlertDialog.Builder(getContext())
                        .setMessage(R.string.text_message_delete)
                        .setNegativeButton(R.string.tv_cancel, null)
                        .setPositiveButton(R.string.btn_user_sure, (dialog, which) ->
                                UserModel.getInstance().deleteBill(data.getId()).unsafeSubscribe(new ServicesResponse<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        EventBus.getDefault().post(data);
                                    }
                                }))
                        .show()
        );
    }

}
