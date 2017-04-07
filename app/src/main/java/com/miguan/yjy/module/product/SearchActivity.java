package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.HistorySearchAdpter;
import com.miguan.yjy.adapter.ProductAllSearchAdapter;
import com.miguan.yjy.adapter.viewholder.RecommedViewholder;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.widget.FlowTagLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @作者 cjh
 * @日期 2017/3/20 10:45
 * @描述 搜索框
 */
@RequiresPresenter(SearchActivityPresenter.class)
public class SearchActivity extends BaseListActivity<SearchActivityPresenter> {

    @BindView(R.id.edt_product_search)
    EditText edtSearch;
    @BindView(R.id.img_product_search_cancle)
    ImageView imgSearchCancle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.flowtag_product_all_search)
    FlowTagLayout flowtagAllSearch;
    @BindView(R.id.recy_product_history_search)
    EasyRecyclerView recyHistorySearch;
    @BindView(R.id.ll_product_his_search)
    LinearLayout llHisSearch;
    @BindView(R.id.ll_product_search_first)
    LinearLayout llSearchFirst;

    @BindView(R.id.ll_product_search_sencond)
    LinearLayout llSearchSencond;
    @BindView(R.id.tv_product_clear_his)
    TextView tvClearHis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initListener();
        recyHistorySearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected int getLayout() {
        return R.layout.product_activity_search;
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new RecommedViewholder(parent);
    }

    private void initListener() {
        imgSearchCancle.setOnClickListener(v -> clearStr());
        tvClearHis.setOnClickListener(v -> clearHis());
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    llSearchFirst.setVisibility(View.VISIBLE);
                    llSearchSencond.setVisibility(View.GONE);
                } else {
                    llSearchFirst.setVisibility(View.GONE);
                    llSearchSencond.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setData(List<Product> datas) {

        ProductAllSearchAdapter productAllSearchAdapter = new ProductAllSearchAdapter(SearchActivity.this, datas);
        flowtagAllSearch.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        flowtagAllSearch.setAdapter(productAllSearchAdapter);
        productAllSearchAdapter.onlyAddAll(datas);

        recyHistorySearch.setAdapter(new HistorySearchAdpter(SearchActivity.this,datas));


    }

    private void clearStr() {
        edtSearch.setText("");
    }

    private void clearHis() {
        llHisSearch.setVisibility(View.GONE);
        tvClearHis.setVisibility(View.GONE);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setRefreshAble(false);
    }

}
