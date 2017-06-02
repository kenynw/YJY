package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.BrandMoreAdapter;
import com.miguan.yjy.adapter.BrandPagerAdapter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.BrandAll;
import com.miguan.yjy.model.bean.Product;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/5/19 15:57
 * @描述
 */

public class StarProductPresenter extends BaseListFragmentPresenter<StarProductFragment, Product> {

    private long brandId;

    @Override
    protected void onCreate(StarProductFragment view, Bundle saveState) {
        super.onCreate(view, saveState);
        brandId = getView().getArguments().getLong(BrandPagerAdapter.EXTRA_BRAND_ID);
    }

    @Override
    protected void onCreateView(StarProductFragment view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        ProductModel.getInstance().getBrandInfo(brandId)
                .flatMap(new Func1<BrandAll, Observable<List<Product>>>() {
                    @Override
                    public Observable<List<Product>> call(BrandAll brandAll) {
                        getAdapter().removeAllFooter();
                        getAdapter().addFooter(new RecyclerArrayAdapter.ItemView() {
                            @Override
                            public View onCreateView(ViewGroup parent) {
                                View moreBrand = View.inflate(getView().getActivity(), R.layout.foot_more_brand, null);
                                RecyclerView recy_brand_more = (RecyclerView) moreBrand.findViewById(R.id.recy_brand_more);
                                recy_brand_more.setLayoutManager(new LinearLayoutManager(getView().getActivity(), LinearLayoutManager.HORIZONTAL, false));
                                BrandMoreAdapter brandMoreAdapter = new BrandMoreAdapter(getView().getActivity(), brandAll.getOtherBrand());
                                recy_brand_more.setAdapter(brandMoreAdapter);
                                brandMoreAdapter.notifyDataSetChanged();
                                return moreBrand;
                            }

                            @Override
                            public void onBindView(View headerView) {

                            }
                        });
                        return ProductModel.getInstance().getProductList(brandId, 1, 1);
                    }
                }).unsafeSubscribe(getRefreshSubscriber());
    }
}
