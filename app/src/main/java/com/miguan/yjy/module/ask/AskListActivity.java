package com.miguan.yjy.module.ask;

import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miguan.yjy.module.ask.AskListActivityPresenter.EXTRA_PRODUCT_ID;

/**
 * Copyright (c) 2017/6/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(AskListActivityPresenter.class)
public class AskListActivity extends BaseListActivity<AskListActivityPresenter> {

    @BindView(R.id.dv_ask_product_image)
    SimpleDraweeView mDvProductImage;

    @BindView(R.id.tv_ask_product_name)
    TextView mTvProductName;

    @BindView(R.id.tv_ask_list_empty)
    TextView mTvEmpty;

    @BindView(R.id.fl_ask_list_to_ask)
    FrameLayout mFlToAsk;

    private Ask mAsk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("问大家");
        ButterKnife.bind(this);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return viewType == 1 ? new AskProductViewHolder(parent, getIntent().getIntExtra(EXTRA_PRODUCT_ID, 0))
                : new AskTitleViewHolder(parent);
    }

    @Override
    protected int getLayout() {
        return R.layout.ask_activity_list;
    }

    @Override
    public int getViewType(int position) {
        return mAsk.getType();
    }

    public void setData(Ask ask) {
        getPresenter().getAdapter().setOnItemClickListener(position -> {
            Ask item = getPresenter().getItem(position);
            if (ask.getType() == 2) AddAskActivityPresenter.start(AskListActivity.this, ask, item.getSubject());
        });
        mDvProductImage.setImageURI(Uri.parse(ask.getProduct_img()));

        String productName = ask.getProduct_name();
        if (productName.length() > 10) {
            productName = productName.subSequence(0, 10) + "...";
        }
        mTvProductName.setText(Html.fromHtml(String.format(getString(R.string.text_ask_product_name), productName)));
        mTvEmpty.setVisibility(ask.getType() == 2 ? View.VISIBLE : View.GONE);
        mFlToAsk.setOnClickListener(v -> AddAskActivityPresenter.start(this, ask, ""));
        mAsk = ask;
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig()
                .setFooterErrorAble(false)
                .setLoadMoreAble(false)
                .setNoMoreAble(false);
    }

}
