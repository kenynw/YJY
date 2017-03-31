package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.LUtils;

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

    private int mProductId;

    @Override
    protected void onCreate(ProductDetailActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mProductId = getView().getIntent().getIntExtra(EXTRA_PRODUCT_ID, 0);
    }

    @Override
    protected void onCreateView(ProductDetailActivity view) {
        super.onCreateView(view);
        loadData();
    }

    private void loadData() {
        ProductModel.getInstantce().getProductDetail(226104).unsafeSubscribe(getDataSubscriber());
    }

    // 长草
    public void like() {
        ProductModel.getInstantce().like(226104).unsafeSubscribe(new ServicesResponse<Product>() {
            @Override
            public void onNext(Product product) {
                LUtils.toast("长草成功");
            }
        });
    }

}
