package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.HistorySearchAdpter;
import com.miguan.yjy.adapter.ProductAllSearchAdapter;
import com.miguan.yjy.adapter.viewholder.RecommedViewholder;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.SystemPreferences;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.FlowTagLayout;

import java.util.ArrayList;
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
    @BindView(R.id.iv_product_search_clear)
    ImageView imgSearchCancle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.flowtag_product_all_search)
    FlowTagLayout flowtagAllSearch;
    @BindView(R.id.recy_product_history_search)
    EasyRecyclerView recyHistorySearch;
    @BindView(R.id.recycle)
    EasyRecyclerView mRecycle;
    @BindView(R.id.ll_product_his_search)
    LinearLayout llHisSearch;
    @BindView(R.id.ll_product_search_first)
    LinearLayout llSearchFirst;

    @BindView(R.id.ll_product_search_sencond)
    LinearLayout llSearchSencond;
//    @BindView(R.id.tv_product_clear_his)
//    TextView tvClearHis;

    HistorySearchAdpter mHistorySearchAdpter;
    private List<String> mKeywordList = new ArrayList<>();
    private String productNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        productNum = getIntent().getStringExtra("productNum");
        initListener();
        refreshAdapter();
    }

    @Override
    protected int getLayout() {
        return R.layout.product_activity_search;
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        RecommedViewholder recommedViewholder = new RecommedViewholder(parent, edtSearch.getText().toString());
        recommedViewholder.setIsRecyclable(false);
        return recommedViewholder;
    }

    private void initListener() {

        recyHistorySearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        edtSearch.setHint(productNum);
        imgSearchCancle.setOnClickListener(v -> clearStr());
//        tvClearHis.setOnClickListener(v -> clearHis());
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                if (TextUtils.isEmpty(s)) {
                    imgSearchCancle.setVisibility(View.GONE);
                    llSearchFirst.setVisibility(View.VISIBLE);
                    llSearchSencond.setVisibility(View.GONE);
                    tvCancel.setText(R.string.tv_cancel);
                } else {
                    imgSearchCancle.setVisibility(View.VISIBLE);
                    llSearchFirst.setVisibility(View.GONE);
                    getPresenter().onRefresh();
                }
            }
        });


        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    LUtils.closeKeyboard(edtSearch);
                    gotoSearchResult(edtSearch.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void setData(List<Product> datas) {

        ProductAllSearchAdapter productAllSearchAdapter = new ProductAllSearchAdapter(SearchActivity.this, datas);
        flowtagAllSearch.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        flowtagAllSearch.setAdapter(productAllSearchAdapter);
        productAllSearchAdapter.onlyAddAll(datas);

    }

    public void refreshAdapter() {
        String historyKeywords = SystemPreferences.getSearchName();
        if (!TextUtils.isEmpty(historyKeywords)) {
            llHisSearch.setVisibility(View.VISIBLE);
            List<String> keywordList = new ArrayList<>();
            for (String name : historyKeywords.split(",")) {
                keywordList.add(name);
            }
            mKeywordList = keywordList;
        } else {
            llHisSearch.setVisibility(View.GONE);
        }
        mHistorySearchAdpter = new HistorySearchAdpter(SearchActivity.this, mKeywordList);
        recyHistorySearch.setAdapter(mHistorySearchAdpter);
        mHistorySearchAdpter.setRemoveClicklinsener(new HistorySearchAdpter.SetOnRemoveClicklinsener() {
            @Override
            public void removeName(String data) {
                mHistorySearchAdpter.remove(data);
                if (mKeywordList.size() == 1) {
                    clearHis();
                }
                mKeywordList.remove(mKeywordList.indexOf(data));
                removeCurName(mKeywordList);
                mHistorySearchAdpter.notifyDataSetChanged();
            }
        });
        mHistorySearchAdpter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = View.inflate(SearchActivity.this, R.layout.foot_clear_history, null);
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                headerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearHis();
                    }
                });
            }
        });

    }

    private void clearStr() {
        edtSearch.setText("");
    }

    private void clearHis() {
        llHisSearch.setVisibility(View.GONE);
//        tvClearHis.setVisibility(View.GONE);
        SystemPreferences.clear();
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setRefreshAble(false).setLoadMoreAble(false);
    }

    private void removeCurName(List<String> names) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            sb.append(names.get(i) + ",");
        }
        SystemPreferences.setSearchName(sb.toString());
    }

    public static void start(Context context, String productNum) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("productNum", productNum);
        context.startActivity(intent);
    }

    public void gotoSearchResult(String name) {
        LUtils.log("dsdsdfs");
        SearchResultPresenter.start(SearchActivity.this, name, 0, "");
        String oldName = SystemPreferences.getSearchName();
        if (!TextUtils.isEmpty(oldName)) {
            if (!oldName.contains(name))
                SystemPreferences.setSearchName(name + "," + oldName);
        } else {
            SystemPreferences.setSearchName(name + ",");
        }
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();
    }
}
