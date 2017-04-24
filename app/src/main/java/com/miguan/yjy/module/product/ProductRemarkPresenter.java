package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.module.account.LoginActivity;

/**
 * @作者 cjh
 * @日期 2017/3/24 18:20
 * @描述
 */

public class ProductRemarkPresenter extends Presenter<ProductRemarkActivity> {

    public static final String EXTRA_PRODUCT = "product";

    public static void start(Context context, Product product) {
        if (UserPreferences.getUserID() > 0) {
            Intent intent = new Intent(context,ProductRemarkActivity.class);
            intent.putExtra(EXTRA_PRODUCT,product);
            context.startActivity(intent);
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

}
