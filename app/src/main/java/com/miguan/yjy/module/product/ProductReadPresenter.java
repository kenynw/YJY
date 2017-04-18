package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.Product;

/**
 * @作者 cjh
 * @日期 2017/3/29 10:52
 * @描述
 */

public class ProductReadPresenter extends BaseDataActivityPresenter<ProductReadActivity, Component> {
    public static final String EXTRA_PRODUCT_COMPONENT = "component";
   public  Product product;

    public static void start(Context context, Product product) {
        Intent intent = new Intent(context, ProductReadActivity.class);
        intent.putExtra(EXTRA_PRODUCT_COMPONENT, product);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(ProductReadActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
    }

    @Override
    protected void onCreateView(ProductReadActivity view) {
        super.onCreateView(view);
        product  = getView().getIntent().getParcelableExtra(EXTRA_PRODUCT_COMPONENT);
    }
}
