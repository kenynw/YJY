package com.miguan.yjy.module.product;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Category;

/**
 * @作者 cjh
 * @日期 2017/8/29 9:32
 * @描述
 */

public class ProductSortPresenter extends BaseListActivityPresenter<ProductSortActivity,Category> {

    @Override
    protected void onCreateView(ProductSortActivity view) {
        super.onCreateView(view);
        ProductModel.getInstance().querySortProduct().unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onRefresh() {

    }
}
