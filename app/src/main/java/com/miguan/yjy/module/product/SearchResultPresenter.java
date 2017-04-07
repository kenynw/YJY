package com.miguan.yjy.module.product;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.services.ServicesResponse;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/3/21 11:31
 * @描述
 */

public class SearchResultPresenter extends BaseListActivityPresenter<SearchResultActivity, Product> {

    @Override
    protected void onCreateView(SearchResultActivity view) {
        super.onCreateView(view);
        onRefresh();
        ProductModel.getInstantce().getSearchList().subscribe(new ServicesResponse<List<Product>>(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onNext(List<Product> list) {
                getView().setData(list);
            }
        });
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstantce().getSearchList().unsafeSubscribe(getRefreshSubscriber());
    }

}
