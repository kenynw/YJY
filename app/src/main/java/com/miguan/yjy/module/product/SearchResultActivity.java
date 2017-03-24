package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;

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


    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.product_activity_search_result;
    }

    private void setData() {
        tvAll.setOnClickListener(v -> refreshData());
        tvSort.setOnClickListener(v -> sortData());
        tvFunction.setOnClickListener(v -> fuctionData());
        tvFeedback.setOnClickListener(v -> finish());
    }

    private void refreshData() {

    }

    private void sortData() {
        PopupWindow popupWindow = new PopupWindow();
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
