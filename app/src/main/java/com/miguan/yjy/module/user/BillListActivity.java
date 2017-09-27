package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.dialogs.BaseAlertDialog;
import com.miguan.yjy.model.constant.Constants;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/9/21. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(BillListPresenter.class)
public class BillListActivity extends BaseListActivity<BillListPresenter> implements BaseAlertDialog.OnButtonClickListener {

    @BindView(R.id.fl_bill_new)
    FrameLayout mFlNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_bills);
        ButterKnife.bind(this);
        mFlNew.setOnClickListener(v ->
                BaseAlertDialog.newInstance(R.layout.dialog_product_new_bills, this)
                        .show(getSupportFragmentManager(), Constants.TAG_DIALOG_NEW_BILL)
        );
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new BillViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig()
                .setContainerLayoutRes(R.layout.user_bill_list_activity)
                .setFooterNoMoreRes(R.layout.include_default_footer);
    }

    @Override
    public void onPositiveClick(@NonNull View view) {
        EditText etContent = view.getRootView().findViewById(R.id.et_new_bills_name);
        if (etContent != null) {
            String content = etContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                LUtils.toast("清单名不能为空");
            } else {
                getPresenter().newBill(content);
            }
            LUtils.closeKeyboard(etContent);
        }
    }

    @Override
    public void onNegativeClick(@NonNull View view) {

    }

}
