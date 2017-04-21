package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.Product;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/3/29 19:48
 * @描述 成分表
 */

public class ProductComponentListPresenter extends BaseDataActivityPresenter<ProductComponentListActivity, Component> {
    public static final String EXTRA_PRODUCT_COMPONENT = "component";
    public List<Component> mComponents;
    Product product;

    public static void start(Context context, Product product) {
        Intent intent = new Intent(context, ProductComponentListActivity.class);
        intent.putExtra(EXTRA_PRODUCT_COMPONENT, product);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(ProductComponentListActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        product = getView().getIntent().getParcelableExtra(EXTRA_PRODUCT_COMPONENT);
        mComponents = product.getComponentList();
    }


    @Override
    protected void onCreateView(ProductComponentListActivity view) {
        super.onCreateView(view);

    }
}
