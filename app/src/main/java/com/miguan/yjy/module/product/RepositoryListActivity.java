package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.user.FeedbackActivity;

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
        RepositoryViewHolder repositoryViewHolder = new RepositoryViewHolder(parent);
        repositoryViewHolder.setOnDeleteListener(getPresenter());
        return repositoryViewHolder;
    }

    @Override
    public ListConfig getListConfig() {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_search_list, null, false);
        TextView textView = ButterKnife.findById(view, R.id.tv_product_feedback);
        textView.setOnClickListener(v -> startActivity(new Intent(this, FeedbackActivity.class)));
        return super.getListConfig()
                .setContainerEmptyView(view)
                .setContainerLayoutRes(R.layout.product_activity_repository_list);
    }

    @Override
    public void onRefresh() {
        getPresenter().onRefresh();
    }

    public void setCount(int count) {
        mTvCount.setText(Html.fromHtml(String.format(getString(R.string.text_search_count), count)));
        mInputPanel.tvCancel.setText(count == 0 ? "添加" : "取消");
        mInputPanel.tvCancel.setOnClickListener(v -> {
            if (count == 0 && mInputPanel.getInputText().length() > 0) {
                Product product = new Product();
                product.setProduct_name(mInputPanel.getInputText());
                product.setPrice("0");
                product.setLocal(true);
                product.setProduct_img("");
                getPresenter().insertLocalProduct(product);
                Intent intent = new Intent();
                intent.putExtra("product", product);
                setResult(Activity.RESULT_OK, intent);
            }
            finish();
        });
    }

    public SearchInputPanel getInputPanel() {
        return mInputPanel;
    }

}
