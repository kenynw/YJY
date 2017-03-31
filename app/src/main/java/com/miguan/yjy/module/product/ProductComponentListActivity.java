package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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
 * @日期 2017/3/29 19:46
 * @描述 成分表
 */
@RequiresPresenter(ProductComponentListPresenter.class)
public class ProductComponentListActivity extends BaseDataActivity<ProductComponentListPresenter, Component> {
    @BindView(R.id.tv_product_conponent_num)
    TextView mTvProductConponentNum;
    @BindView(R.id.recy_product_component_create)
    RecyclerView mRecyProductComponentCreate;
    @BindView(R.id.tv_product_create_img)
    TextView mTvProductCreateImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_componentlist);
        ButterKnife.bind(this);
        setToolbarTitle("成分表");
        initData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        mRecyProductComponentCreate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ProductReadAdapter productReadAdapter = new ProductReadAdapter(ProductComponentListActivity.this, ProductModel.getInstantce().getReadListData());
        mRecyProductComponentCreate.setAdapter(productReadAdapter);
    }

}
