package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.Product;

/**
 * @作者 cjh
 * @日期 2017/3/29 10:52
 * @描述
 */

public class ProductReadPresenter extends BaseListActivityPresenter<ProductReadActivity, Component> {
    public static final String EXTRA_PRODUCT_COMPONENT = "component";
    public static final String EXTRA_PRODUCT_COMPONENT_TYPE = "component_type";
    public static final String EXTRA_PRODUCT_COMPONENT_POSITION = "component_position";
    public Product product;
    public int type;
    public int position;

    public static void start(Context context, Product product, int type, int position) {
        Intent intent = new Intent(context, ProductReadActivity.class);
        intent.putExtra(EXTRA_PRODUCT_COMPONENT, product);
        intent.putExtra(EXTRA_PRODUCT_COMPONENT_TYPE, type);
        intent.putExtra(EXTRA_PRODUCT_COMPONENT_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(ProductReadActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        product = getView().getIntent().getParcelableExtra(EXTRA_PRODUCT_COMPONENT);
        type = getView().getIntent().getIntExtra(EXTRA_PRODUCT_COMPONENT_TYPE, -1);
        position = getView().getIntent().getIntExtra(EXTRA_PRODUCT_COMPONENT_POSITION, -1);
    }

    @Override
    protected void onCreateView(ProductReadActivity view) {
        super.onCreateView(view);
    }
}
