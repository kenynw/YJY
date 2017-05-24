package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/5/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(RepositoryListPresenter.class)
public class RepositoryListActivity extends BaseListActivity<RepositoryListPresenter> implements SearchInputPanel.SearchListener {

    @BindView(R.id.tv_product_list_count)
    TextView mTvCount;

    private SearchInputPanel mInputPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mInputPanel = new SearchInputPanel(this, this);
        mInputPanel.setEtHint("输入产品关键词");
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(parent);
    }

    @Override
    protected int getLayout() {
        return R.layout.product_activity_repository_list;
    }

    @Override
    public void onRefresh() {
        getPresenter().onRefresh();
    }

    public void setCount(int count) {
        mTvCount.setText(Html.fromHtml(String.format(getString(R.string.text_search_count), count)));
        mInputPanel.tvCancel.setText(count == 0 ? "添加" : "取消");
        mInputPanel.tvCancel.setOnClickListener(v -> {
            Product product = new Product();
//            product.setProduct_name(panel.getInputText());
            product.setPrice("0");
        });
    }

    public SearchInputPanel getInputPanel() {
        return mInputPanel;
    }

}
