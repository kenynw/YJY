package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.ProductReadAdapter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Component;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/29 9:57
 * @描述 官方解读
 */
@RequiresPresenter(ProductReadPresenter.class)
public class ProductReadActivity extends BaseDataActivity<ProductReadPresenter, Component> implements View.OnClickListener {
    @BindView(R.id.recy_product_read)
    RecyclerView mRecyProductRead;
    @BindView(R.id.tv_product_compare)
    TextView mTvProductCompare;
    @BindView(R.id.tv_product_no_compare)
    TextView mTvProductNoCompare;
    @BindView(R.id.tv_product_safe)
    TextView mTvProductSafe;
    @BindView(R.id.tv_product_function_info)
    TextView mTvProductFunctionInfo;

    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_read);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.tv_title_read);
        initData();
        initListener();
    }


    private void initData() {
        mRecyProductRead.setFocusable(false);
        mRecyProductRead.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ProductReadAdapter productReadAdapter = new ProductReadAdapter(ProductReadActivity.this, ProductModel.getInstantce().getReadListData());
        mRecyProductRead.setAdapter(productReadAdapter);
    }

    private void initListener() {
        mTvProductCompare.setSelected(true);
        mTvProductCompare.setOnClickListener(this);
        mTvProductNoCompare.setOnClickListener(this);
        mTvProductSafe.setOnClickListener(this);
        mTvProductFunctionInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_product_compare:
                mTvProductCompare.setSelected(true);
                mTvProductNoCompare.setSelected(false);
                mTvProductSafe.setSelected(false);
                mTvProductFunctionInfo.setSelected(false);
                break;
            case R.id.tv_product_no_compare:
                mTvProductCompare.setSelected(false);
                mTvProductNoCompare.setSelected(true);
                mTvProductSafe.setSelected(false);
                mTvProductFunctionInfo.setSelected(false);
                break;
            case R.id.tv_product_safe:
                mTvProductCompare.setSelected(false);
                mTvProductNoCompare.setSelected(false);
                mTvProductSafe.setSelected(true);
                mTvProductFunctionInfo.setSelected(false);
                break;
            case R.id.tv_product_function_info:
                mTvProductCompare.setSelected(false);
                mTvProductNoCompare.setSelected(false);
                mTvProductSafe.setSelected(false);
                mTvProductFunctionInfo.setSelected(true);
                break;
        }
    }


}
