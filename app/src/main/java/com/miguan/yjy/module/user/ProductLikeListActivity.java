package com.miguan.yjy.module.user;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.ProductLikeViewHolder;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.module.product.SearchFilterPanel;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ProductLikeListPresenter.class)
public class ProductLikeListActivity extends BaseListActivity<ProductLikeListPresenter> {

    private boolean mIsInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_like);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new ProductLikeViewHolder(parent);
    }

    @Override
    protected int getLayout() {
        return R.layout.user_activity_like;
    }

    @Override
    public ListConfig getListConfig() {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_common_list, null);
        TextView tv = view.findViewById(R.id.tv_empty);
        tv.setText("还没有添加长草的产品哦~");

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_empty_like);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);

        return super.getListConfig()
                .setContainerLayoutRes(R.layout.user_activity_like)
                .setFooterNoMoreRes(R.layout.include_default_footer)
                .setContainerEmptyView(view);
    }

    public void setData(ProductList productList) {
        if (mIsInit) return;
        SearchFilterPanel filterPanel = new SearchFilterPanel(this, productList.getCategoryList(), productList.getEffectList());
        filterPanel.setOnItemSelectedListener(new SearchFilterPanel.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int cateId, String text) {
                filterPanel.dismissMenu();
                if (cateId > 0) {
                    getPresenter().setCateId(cateId);
                }
                if (!TextUtils.isEmpty(text)) {
                    getPresenter().setEffect(text);
                }
                if (cateId <= 0 && text.isEmpty()) {
                    getPresenter().setEffect("");
                    getPresenter().setCateId(0);
                }
                getPresenter().onRefresh();
            }

            @Override
            public void onMenuCheck() {
            }

            @Override
            public void onSencondItemSelected(int id, String text) {

            }
        });
        mIsInit = true;
    }

}
