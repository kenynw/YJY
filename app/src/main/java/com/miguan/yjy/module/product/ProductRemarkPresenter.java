package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.ImageModel;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.Result;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.utils.LUtils;

import java.io.File;

import rx.Observable;
import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/3/24 18:20
 * @描述
 */
public class ProductRemarkPresenter extends Presenter<ProductRemarkActivity> {

    public static final String EXTRA_PRODUCT = "mProduct";

    public static void start(Context context, Product product) {
        if (AccountModel.getInstance().isLogin()) {
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

    public void submit(int star, String content, Uri uri) {
        ServicesResponse<Result> response = new ServicesResponse<Result>() {
            @Override
            public void onNext(Result result) {
                getView().getExpansionDelegate().hideProgressBar();
                if (result.getFlag() == 1) LUtils.toast(result.getContent());
                getView().finish();
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        if (uri != null) {
            ImageModel.getInstance().uploadImageSync("attachment", new File(uri.getPath()))
                    .flatMap(new Func1<String, Observable<Result>>() {
                        @Override
                        public Observable<Result> call(String s) {
                            return ProductModel.getInstance().addEvaluate(mProduct.getId(), star, s, content);
                        }
                    })
                    .unsafeSubscribe(response);

        } else {
            ProductModel.getInstance().addEvaluate(mProduct.getId(), star, "", content)
                    .subscribe(response);
        }
    }

}
