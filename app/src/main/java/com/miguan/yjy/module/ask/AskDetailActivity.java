package com.miguan.yjy.module.ask;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/26. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(AskDetailActivityPresenter.class)
public class AskDetailActivity extends BaseListActivity<AskDetailActivityPresenter> implements TextWatcher {

    @BindView(R.id.dv_detail_top_thumb)
    SimpleDraweeView mDvProductThumb;

    @BindView(R.id.tv_detail_top_product)
    TextView mTvProductName;

    @BindView(R.id.ll_detail_top_product)
    LinearLayout mLlProduct;

    @BindView(R.id.tv_ask_detail_title)
    TextView mTvAskTitle;

    @BindView(R.id.et_input_input)
    EditText mEtInput;

    @BindView(R.id.tv_input_send)
    TextView mTvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_ask_detail);
        ButterKnife.bind(this);

        mEtInput.addTextChangedListener(this);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new AnswerViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        DividerDecoration decoration = new DividerDecoration(0xFFEBEBEB, LUtils.dp2px(1), LUtils.dp2px(78), LUtils.dp2px(15));
        decoration.setDrawLastItem(false);

        return super.getListConfig()
                .setFooterErrorAble(false)
                .setRefreshAble(false)
                .setContainerLayoutRes(R.layout.ask_activity_detail)
                .setContainerEmptyRes(R.layout.empty_ask_detail_list)
                .setItemDecoration(decoration);
    }

    private void checkInput(String productName) {
        if (mEtInput.getText().toString().trim().length() <= 0) {
            LUtils.toast("内容不能为空");
            return;
        }

        getPresenter().send(productName, mEtInput.getText().toString().trim());
    }

    public void setData(Ask ask) {
        mLlProduct.setOnClickListener(v -> ProductDetailPresenter.start(this, ask.getProduct_id()));
        mDvProductThumb.setImageURI(ask.getProduct_img());
        mTvProductName.setText(ask.getProduct_name());
        mTvAskTitle.setText(Html.fromHtml(ask.getSubject()));
        mTvSend.setOnClickListener(v -> checkInput(ask.getProduct_name()));
    }

    public void clearInput() {
        mEtInput.setText("");
        LUtils.closeKeyboard(mEtInput);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mTvSend.setEnabled(mEtInput.getText().length() > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public int[] getHideSoftViewIds() {
        return new int[] {R.id.et_input_input};
    }

}
