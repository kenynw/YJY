package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.adapter.EvaluateAdapter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.utils.LUtils;
import com.umeng.socialize.UMShareAPI;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @作者 cjh
 * @日期 2017/3/22 10:12
 * @描述
 */

public class ProductDetailPresenter extends BaseDataActivityPresenter<ProductDetailActivity, Product>
        implements RecyclerArrayAdapter.OnMoreListener{

    public static final String EXTRA_PRODUCT_ID = "product_id";
    public static final String SORT_DEFAULT = "default";
    public static final String SORT_SKIN = "skin";
    public static final String START_PRAISE = "Praise";
    public static final String START_MIDDLE = "middle";
    public static final String START_BAD = "bad";

    private String userEvluate;
    private String sort;

    private int mCurPage = 1;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getUserEvluate() {
        return userEvluate;
    }

    public void setUserEvluate(String userEvluate) {
        this.userEvluate = userEvluate;
    }
    //
//    condition(string) {
//    } －筛选星级,目前有'Praise'好评,'middle'中评,'bad'差评

    public static void start(Context context, int productId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
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
        ProductModel.getInstance().getProductDetail(mProductId).unsafeSubscribe(getDataSubscriber());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEvaluateData(SORT_DEFAULT, null);
    }

    // 长草
    public void addLike(boolean isLike) {
        if (isLogin())
            ProductModel.getInstance().addLike(mProductId).unsafeSubscribe(new ServicesResponse<String>() {
                @Override
                public void onNext(String result) {
                    getView().setLike(!isLike);
                    LUtils.toast(isLike ? "取消成功" : "长草成功");
                }
            });
    }

    public void setOrder(String order) {

    }

    public void getEvaluateData(String orderBy, String condition) {
        setSort(orderBy);
        setUserEvluate(condition);
        ProductModel.getInstance().getEvaluate(mProductId, 1, orderBy, condition).subscribe(new ServicesResponse<List<Evaluate>>() {
            @Override
            public void onNext(List<Evaluate> evaluates) {
                getView().setEvaluate(evaluates);
                mCurPage = 2;
            }
        });
    }

    private boolean isLogin() {
        if (UserPreferences.getUserID() <= 0) {
            getView().startActivity(new Intent(getView(), LoginActivity.class));
            return false;
        }
        return true;
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        UMShareAPI.get(getView()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMoreShow() {
        ProductModel.getInstance().getEvaluate(mProductId, mCurPage, sort, userEvluate).subscribe(new ServicesResponse<List<Evaluate>>() {
            @Override
            public void onNext(List<Evaluate> evaluates) {
                ((EvaluateAdapter) getView().mRecyEvalutate.getAdapter()).addAll(evaluates);
                mCurPage++;
            }
        });
    }

    @Override
    public void onMoreClick() {

    }
}
