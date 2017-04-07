package com.miguan.yjy.module.product;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.ProductSortAdapter;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/21 11:30
 * @描述 搜索结果页
 */
@RequiresPresenter(SearchResultPresenter.class)
public class SearchResultActivity extends BaseListActivity<SearchResultPresenter> {
    @BindView(R.id.edt_product_search)
    EditText edtSearch;
    @BindView(R.id.img_product_search_cancle)
    ImageView imgSearchCancle;
    @BindView(R.id.tv_product_all)
    TextView tvAll;
    @BindView(R.id.tv_product_sort)
    TextView tvSort;
    @BindView(R.id.tv_product_function)
    TextView tvFunction;
    @BindView(R.id.tv_product_num)
    TextView tvNum;
    @BindView(R.id.ll_product_sencond_result)
    LinearLayout llSencondResult;
    @BindView(R.id.tv_product_feedback)
    TextView tvFeedback;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_product_sort)
    LinearLayout mLlProductSort;
    @BindView(R.id.ll_product_function)
    LinearLayout mLlProductFunction;
    @BindView(R.id.recycle)
    EasyRecyclerView mRecycle;
    @BindView(R.id.ll_product_first_result)
    LinearLayout mLlProductFirstResult;
    @BindView(R.id.ll_product_sort_all)
    LinearLayout mLlProductSortAll;


    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.product_activity_search_result;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setData(List<Product> list) {
        tvAll.setOnClickListener(v -> refreshData());
        tvSort.setOnClickListener(v -> sortData(list));
        mLlProductSort.setOnClickListener(v -> sortData(list));
        tvFunction.setOnClickListener(v -> fuctionData());
        ivBack.setOnClickListener(v -> finish());

    }

    private void refreshData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sortData(List<Product> list) {
        View sortView = View.inflate(SearchResultActivity.this, R.layout.popwindow_product_sort, null);
        RecyclerView recyclerView = ButterKnife.findById(sortView, R.id.recy_product_name);
        recyclerView.setLayoutManager(new GridLayoutManager(SearchResultActivity.this, 2));
        recyclerView.setAdapter(new ProductSortAdapter(SearchResultActivity.this, list));
        PopupWindow popupWindow = new PopupWindow(sortView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        popupWindow.setBackgroundDrawable(bg);
        popupWindow.showAsDropDown(mLlProductSortAll, 0, 0, Gravity.BOTTOM);

    }

    private void fuctionData() {

    }

    private void gotofeedback() {

    }

//    @Override
//    public ListConfig getListConfig() {
//        return super.getListConfig().setItemDecoration(new DividerDecoration(getResources().getColor(R.color.f5),1,15,15));
//    }
}
