package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.dsk.chain.expansion.data.BaseDataActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/7/27. LiaoPeiKun Inc. All rights reserved.
 */

public class BillboardActivity extends BaseDataActivity<BillboardActivityPresenter, Product> {

    @BindView(R.id.recy_billboard_list)
    RecyclerView mRecyList;

    @BindView(R.id.recy_billboard_other)
    RecyclerView mRecyOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_billboard);
        ButterKnife.bind(this);

        mRecyList.setLayoutManager(new LinearLayoutManager(this));
        mRecyList.setAdapter(new RecyclerArrayAdapter(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new BillboardProductViewHolder(parent);
            }
        });
        mRecyOther.setLayoutManager(new LinearLayoutManager(this));
        mRecyOther.setAdapter(new RecyclerArrayAdapter(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new BillboardViewHolder(parent);
            }
        });
    }

    @Override
    public void setData(Product product) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Product data = getPresenter().getData();
        if (data != null) {
            String title = "经典口碑产品，让你摆脱选择恐惧症";
//            new SharePopupWindow.Builder(this)
//                    .setTitle(data)
//                    .setContent(data)
//                    .setImageUrl(data.getProduct_img())
//                    .setWxCircleTitle(data.getName() + title)
//                    .setWbContent(data.getName() + title + ", 分享来自#颜究院APP#" + url);
        }
        return super.onOptionsItemSelected(item);
    }
}
