package com.miguan.yjy.module.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.UserProduct;
import com.miguan.yjy.module.account.UserTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/24. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(QueryCodePresenter.class)
public class QueryCodeActivity extends ChainBaseActivity<QueryCodePresenter> {

    @BindView(R.id.et_query_code_brand)
    EditText mEtBrand;

    @BindView(R.id.et_query_code_product)
    EditText mEtProduct;

    @BindView(R.id.tv_query_code_instruction)
    TextView mTvInstruction;

    @BindView(R.id.btn_query_code_submit)
    Button mBtnSubmit;

    private boolean mIsInit = false;

    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_query_code);
        setToolbarTitle(R.string.text_query_production_date);
        ButterKnife.bind(this);

        mProduct = getIntent().getParcelableExtra(QueryCodePresenter.EXTRA_PRODUCT);

        UserTextWatcher watcher = new UserTextWatcher(mBtnSubmit, mEtBrand, mEtProduct);
        mEtBrand.addTextChangedListener(watcher);
        mEtBrand.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && mIsInit) {
                Intent intent = new Intent(this, BrandListActivity.class);
                intent.putExtra(BrandListPresenter.EXTRA_TYPE, 1);
                startActivityForResult(intent, 100);
                mIsInit = true;
            }
        });
        mEtProduct.addTextChangedListener(watcher);

        mEtBrand.setOnClickListener(v -> {
            Intent intent = new Intent(this, BrandListActivity.class);
            intent.putExtra(BrandListPresenter.EXTRA_TYPE, 1);
            startActivityForResult(intent, 100);
            mIsInit = true;
        });
        mTvInstruction.setOnClickListener(v -> startActivity(new Intent(this, InstructionActivity.class)));
        mBtnSubmit.setOnClickListener(v -> getPresenter().query(mEtProduct.getText().toString().trim()));
    }

    public void setBrand(String brandName) {
        mEtBrand.setText(brandName);
        mEtProduct.setText("");
    }

    public void showQueryDialog(UserProduct product, Brand brand) {
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
        tvName.setText(mEtBrand.getText());
        tvCode.setText(String.format(getString(R.string.label_batch_no), mEtProduct.getText()));
        tvMfgDate.setText(product.getStartDay());
        tvExpiration.setText(product.getEndDay());
        btnSave.setOnClickListener(v -> {
            dialog.dismiss();
            AddRepositoryPresenter.start(QueryCodeActivity.this, brand, product.getEndDay(), mProduct);
            finish();
        });
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

}