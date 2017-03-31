package com.miguan.yjy.module.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
        mEtBrand.setText(product.getProduct_name());
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
        Product product = getPresenter().getProduct();
        product.setBrand(mEtBrand.getText().toString().trim());
        product.setProduct_name(mEtProduct.getText().toString().trim());
        showQueryDialog(product);
    }

    private void showQueryDialog(Product product) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_query_result)
                .show();

        ImageView ivClose = ButterKnife.findById(dialog, R.id.iv_query_dialog_close);
        TextView tvName = ButterKnife.findById(dialog, R.id.tv_query_dialog_name);
        TextView tvCode = ButterKnife.findById(dialog, R.id.tv_query_dialog_code);
        TextView tvMfgDate = ButterKnife.findById(dialog, R.id.tv_query_dialog_mfg_date);
        TextView tvExpiration = ButterKnife.findById(dialog, R.id.tv_query_dialog_expiration);
        Button btnSave = ButterKnife.findById(dialog, R.id.btn_query_save);
        ivClose.setOnClickListener(v -> dialog.dismiss());
        tvName.setText("Biotherm 碧欧泉");
        tvCode.setText(String.format(getString(R.string.label_batch_no), "00n100"));
        tvMfgDate.setText("2016年01月01日");
        tvExpiration.setText("2019年01月01日");
        btnSave.setOnClickListener(v -> {
            dialog.dismiss();
            AddRepositoryPresenter.start(QueryCodeActivity.this, product);
        });
    }

}
