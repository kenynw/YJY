package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;

/**
 * @作者 cjh
 * @日期 2017/3/22 10:12
 * @描述
 */

public class ProductDetailPresenter extends BaseDataActivityPresenter<ProductDetailActivity, Product> {

    public static final String EXTRA_PRODUCT_ID = "product_id";

    public static void start(Context context, int productId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateView(ProductDetailActivity view) {
        super.onCreateView(view);
        loadData();
    }

    private void loadData() {
        ProductModel.getInstantce().getProductDetail().unsafeSubscribe(getDataSubscriber());
    }
}
