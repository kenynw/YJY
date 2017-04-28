package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.utils.LUtils;

/**
 * @作者 cjh
 * @日期 2017/3/24 18:20
 * @描述
 */

public class ProductRemarkPresenter extends Presenter<ProductRemarkActivity> {

    public static final String EXTRA_PRODUCT = "mProduct";

    public static void start(Context context, Product product) {
        if (UserPreferences.getUserID() > 0) {
            Intent intent = new Intent(context,ProductRemarkActivity.class);
            intent.putExtra(EXTRA_PRODUCT,product);
            context.startActivity(intent);
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    private Product mProduct;

    @Override
    protected void onCreate(ProductRemarkActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mProduct = getView().getIntent().getParcelableExtra(ProductRemarkPresenter.EXTRA_PRODUCT);
    }

    @Override
    protected void onCreateView(ProductRemarkActivity view) {
        super.onCreateView(view);
        getView().setProduct(mProduct);
    }

    public void submit(int star, String content) {
        ProductModel.getInstance().addEvaluate(mProduct.getId(), star, content)
                .subscribe(new ServicesResponse<String>(){
                    @Override
                    public void onNext(String s) {
                        LUtils.toast("评论成功");
                        getView().finish();
                    }
                });
    }

}
