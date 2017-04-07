package com.miguan.yjy.module.template;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.bean.Product;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class GenTemplatePresenter extends BaseListActivityPresenter<GenTemplateActivity, Product> {

    @Override
    protected void onCreateView(GenTemplateActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        List<Product> list = new ArrayList<>();
        Product product = new Product();
        product.setProduct_name("哈哈哈哈");
        list.add(product);
        Observable.just(list).unsafeSubscribe(getRefreshSubscriber());
    }

}
