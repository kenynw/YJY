package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.EvaluateAdapter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.EntityRoot;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.Product;
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
        implements RecyclerArrayAdapter.OnMoreListener {

    public static final String EXTRA_PRODUCT_ID = "product_id";

    public static final String SORT_DEFAULT = "default";
    public static final String SORT_SKIN = "skin";
    public static final String START_PRAISE = "Praise";
    public static final String START_MIDDLE = "middle";
    public static final String START_BAD = "bad";

    private String mCondition;
    private String mSort;

    private int mCurPage = 1;

    private int mProductId;
    PopupWindow popupWindow;
    View view;
    TextView mTvSure;
    private LinearLayout popPproductExplain;
    private LinearLayout llProductExplain;

    //
//    condition(string) {
//    } －筛选星级,目前有'Praise'好评,'middle'中评,'bad'差评

    public static void start(Context context, int productId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

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
        getEvaluateData();
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

    public void getEvaluateData() {
        ProductModel.getInstance().getEvaluateSecond(mProductId, 1, mSort, mCondition)
                .subscribe(new ServicesResponse<EntityRoot<List<Evaluate>>>() {
                    @Override
                    public void onNext(EntityRoot<List<Evaluate>> listEntityRoot) {
                        getView().setEvaluate(listEntityRoot.getData(), listEntityRoot.getPageTotal());
                        mCurPage = 2;
                    }
                });
    }

    private boolean isLogin() {
        if (!AccountModel.getInstance().isLogin()) {
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
        ProductModel.getInstance().getEvaluate(mProductId, mCurPage, mSort, mCondition)
                .subscribe(new ServicesResponse<List<Evaluate>>() {
                    @Override
                    public void onNext(List<Evaluate> evaluates) {
                        EvaluateAdapter evaluateAdapter = (EvaluateAdapter) getView().mRecyEvalutate.getAdapter();
                        evaluateAdapter.addAll(evaluates);
                        mCurPage++;
                    }
                });
    }

    @Override
    public void onMoreClick() {

    }

    public String getSort() {
        return mSort;
    }

    public void setSort(String sort) {
        this.mSort = sort;
    }

    public void setCondition(String condition) {
        this.mCondition = condition;
    }


    /**
     * 正品保证说明
     */
    public void showExplain() {
        view = View.inflate(getView(), R.layout.popwindow_product_explain, null);
        mTvSure = (TextView) view.findViewById(R.id.tv_sure);
        llProductExplain = view.findViewById(R.id.pop_product_explain);
        popPproductExplain = view.findViewById(R.id.ll_product_explain);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        mTvSure.setOnClickListener(v -> popupWindow.dismiss());
        llProductExplain.setOnClickListener(v -> popupWindow.dismiss());
        popPproductExplain.setOnClickListener(v -> {});
    }
}
