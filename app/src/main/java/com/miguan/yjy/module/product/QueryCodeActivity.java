package com.miguan.yjy.module.product;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/24. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(QueryCodePresenter.class)
public class QueryCodeActivity extends ChainBaseActivity<QueryCodePresenter> {

    @BindView(R.id.tv_query_code_brand)
    TextView mEtBrand;

    @BindView(R.id.et_query_code_product)
    EditText mEtProduct;

    @BindView(R.id.tv_query_code_instruction)
    TextView mTvInstruction;

    @BindView(R.id.btn_query_code_submit)
    Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_query_code);
        setToolbarTitle(R.string.text_query_production_date);
        ButterKnife.bind(this);

        mTvInstruction.setOnClickListener(v -> startActivity(new Intent(this, InstructionActivity.class)));
        mBtnSubmit.setOnClickListener(v -> checkInput());
    }

    public void setData(Product product) {
        mEtBrand.setText(product.getBrand());
    }

    private void checkInput() {
        if (TextUtils.isEmpty(mEtBrand.getText())) {
            LUtils.toast(R.string.hint_brand_name);
            return;
        }
        if (TextUtils.isEmpty(mEtProduct.getText())) {
            LUtils.toast(R.string.hint_product_code);
            return;
        }
        getPresenter().query(mEtBrand.getText().toString().trim(), mEtProduct.getText().toString().trim());
    }

}
