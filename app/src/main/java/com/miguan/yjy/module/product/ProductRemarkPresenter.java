package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.bean.Product;

/**
 * @作者 cjh
 * @日期 2017/3/24 18:20
 * @描述
 */

public class ProductRemarkPresenter extends Presenter<Product> {
    public static String EXTRA_PROUDUCT = "product";

    public static void start(Context context, Product product) {
        Intent intent = new Intent(context,ProductRemarkActivity.class);
        intent.putExtra(EXTRA_PROUDUCT,product);
        context.startActivity(intent);
    }

}
