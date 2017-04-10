package com.miguan.yjy.module.product;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.ProductSortAdapter1;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.ProductInfo;
import com.miguan.yjy.model.services.ServicesResponse;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/21 11:31
 * @描述
 */

public class SearchResultPresenter extends BaseListActivityPresenter<SearchResultActivity, ProductInfo> {

    @Override
    protected void onCreateView(SearchResultActivity view) {
        super.onCreateView(view);
        onRefresh();
        ProductModel.getInstance().getSearchList().subscribe(new ServicesResponse<List<Product>>(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onNext(List<Product> list) {
                getView().setData(list);
            }
        });
    }

    @Override
    public void onRefresh() {
        //    action(string) － 固定值searchQuery
//    keywords(string) － （非必填）关键词
//    type(int) － （非必填）类型 ：不传或者传1为默认搜索产品
//    cate_id(int) － （非必填）分类id
//    effect(string) － （非必填）功效关键字
//    page(int) － （非必填）当前页数
//    pageSize(string) － （非必填）每页多少条

        ProductModel.getInstance()
                .searchQuery(getView().keywords,getView().type,getView().cate_id,getView().effect,1)
                .map(product -> {
                    // TODO 设置分类
                    product.getCategories();
                    product.getEffects();
                    return product.getList();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        ProductModel.getInstance()
                .searchQuery(getView().keywords,getView().type,getView().cate_id,getView().effect,getCurPage())
                .map(product -> {
                    // TODO 设置分类
                    product.getCategories();


                    return product.getList();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sortData(List<Product> list,List<String>effects,int type) {
        View sortView = View.inflate(getView(), R.layout.popwindow_product_sort, null);
        RecyclerView recyclerView = ButterKnife.findById(sortView, R.id.recy_product_name);
        recyclerView.setLayoutManager(new GridLayoutManager(getView(), 2));
        recyclerView.setAdapter(new ProductSortAdapter1(getView(), list,effects,type));
        PopupWindow popupWindow = new PopupWindow(sortView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        popupWindow.setBackgroundDrawable(bg);
        popupWindow.showAsDropDown(getView().mLlProductSortAll, 0, 0, Gravity.BOTTOM);

    }

    private void fuctionData() {

    }

}
