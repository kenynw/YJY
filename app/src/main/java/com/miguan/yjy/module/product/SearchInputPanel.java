package com.miguan.yjy.module.product;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/5/23. LiaoPeiKun Inc. All rights reserved.
 */

public class SearchInputPanel implements TextWatcher, TextView.OnEditorActionListener {

    @BindView(R.id.edt_product_search)
    EditText edtSearch;

    @BindView(R.id.iv_product_search_clear)
    ImageView imgSearchCancle;

    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    private ChainBaseActivity mActivity;

    private SearchListener mListener;

    public SearchInputPanel(ChainBaseActivity activity, SearchListener listener) {
        mActivity = activity;
        mListener = listener;
        ButterKnife.bind(this, mActivity);

        edtSearch.setHint(mActivity.getIntent().getStringExtra("productNum"));
        edtSearch.addTextChangedListener(this);
        edtSearch.setOnEditorActionListener(this);

        imgSearchCancle.setOnClickListener(v -> edtSearch.setText(""));
        tvCancel.setOnClickListener(v -> mActivity.finish());

    }

    public void setEtHint(String text) {
        edtSearch.setHint(text);
    }

    public String getInputText() {
        return edtSearch.getText().toString().trim();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s)) {
            imgSearchCancle.setVisibility(View.GONE);
            tvCancel.setText(R.string.tv_cancel);
        } else {
            imgSearchCancle.setVisibility(View.VISIBLE);
        }
        mListener.onRefresh();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            LUtils.closeKeyboard(edtSearch);
            mListener.onRefresh();
            return true;
        }
        return false;
    }

    public interface SearchListener {
        void onRefresh();
    }

}