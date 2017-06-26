package com.miguan.yjy.module.question;

import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(AskListActivityPresenter.class)
public class AskListActivity extends BaseListActivity<AskListActivityPresenter> {

    private int mType;

    @BindView(R.id.dv_ask_product_image)
    SimpleDraweeView mDvProductImage;

    @BindView(R.id.tv_ask_product_name)
    TextView mTvProductName;

    @BindView(R.id.tv_ask_list_empty)
    TextView mTvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("问大家");
        ButterKnife.bind(this);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return viewType == 1 ? new AskViewHolder(parent) : new AskTitleViewHolder(parent);
    }

    @Override
    protected int getLayout() {
        return R.layout.ask_activity_list;
    }

    @Override
    public int getViewType(int position) {
        return mType;
    }

    public void setData(Ask ask) {
        mDvProductImage.setImageURI(Uri.parse(ask.getProduct_img()));

        String productName = ask.getProduct_name();
        if (productName.length() > 10) {
            productName = productName.subSequence(0, 9) + "...";
        }
        mTvProductName.setText(Html.fromHtml(String.format(getString(R.string.text_ask_product_name), productName)));
        mTvEmpty.setVisibility(ask.getType() == 2 ? View.VISIBLE : View.GONE);
        mType = ask.getType();
    }

}
