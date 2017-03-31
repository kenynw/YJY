package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.bean.Component;

/**
 * @作者 cjh
 * @日期 2017/3/29 10:52
 * @描述
 */

public class ProductReadPresenter extends BaseDataActivityPresenter<ProductReadActivity, Component> {
    public  static final String EXTRA_PRODUCT_COMPONENT="component";
    public static void start(Context context,Component component) {
        Intent intent = new Intent(context, ProductReadActivity.class);
        intent.putExtra(EXTRA_PRODUCT_COMPONENT, component);
        context.startActivity(intent);
    }
}
