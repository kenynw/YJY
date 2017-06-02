package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.model.bean.BrandAll;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/5/18 11:17
 * @描述
 */

public class BrandMainPresenter extends BaseDataActivityPresenter<BrandMainActivity, Brand> {

    public static final String EXTRA_BRAND_ID = "brandId";

    long brandId;
    List<Article> mArticles = new ArrayList<>();

    public static void star(Context context, long brandId) {
        Intent intent = new Intent(context, BrandMainActivity.class);
        intent.putExtra("brandId", brandId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(BrandMainActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        brandId = getView().getIntent().getLongExtra(EXTRA_BRAND_ID, -1);
        onRefresh();

    }

    public void onRefresh() {
        ArticleModel.getInstance().getArticleList(brandId, 1)
                .flatMap(new Func1<List<Article>, Observable<BrandAll>>() {
                    @Override
                    public Observable<BrandAll> call(List<Article> articles) {
                        mArticles = articles;
                        return ProductModel.getInstance().getBrandInfo(brandId);
                    }
                })
                .map(brandAll -> {
                    getView().setData(brandAll.getBrandInfo());
                    return brandAll.getBrandInfo();
                }).unsafeSubscribe(getDataSubscriber());


    }


}
