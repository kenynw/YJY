package com.miguan.yjy.module.product;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.module.user.FeedbackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/21 11:30
 * @描述 搜索结果页
 */
@RequiresPresenter(SearchResultPresenter.class)
public class SearchResultActivity extends BaseListActivity<SearchResultPresenter> {

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.et_product_keywords)
    EditText mEtKeywords;

    @BindView(R.id.iv_product_search_clear)
    ImageView imgSearchCancle;

    @BindView(R.id.tv_product_list_count)
    TextView mTvCount;

    private FilterPanel mFilterPanel;

    private boolean mIsInit = false;

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mIvBack.setOnClickListener(v -> finish());
    }

    @Override
    public ListConfig getListConfig() {
        View view = getLayoutInflater().inflate(R.layout.empty_search_list, getListView());
        View tvFeedback = ButterKnife.findById(view, R.id.tv_product_feedback);
        tvFeedback.setOnClickListener(v -> startActivity(new Intent(this, FeedbackActivity.class)));
        return super.getListConfig().setContainerLayoutRes(R.layout.product_activity_search_result)
                .setContainerEmptyView(view);
    }

    public void setData(String keywords, ProductList productList, String cateName) {
        mTvCount.setText(String.format(getString(R.string.text_search_count), productList.getPageTotal()));
        if (mIsInit) return;
        mEtKeywords.setText(keywords);
        mEtKeywords.requestFocus();
        mFilterPanel = new FilterPanel(this, productList.getCategroy(), productList.getEffects());
        mFilterPanel.setMenuText(1, cateName);
        mFilterPanel.setOnItemSelectedListener((cateId, text) -> {
            mFilterPanel.dismissMenu();
            if (cateId > 0) {
                getPresenter().setCateId(cateId);
            }
            if (!TextUtils.isEmpty(text)) {
                getPresenter().setEffect("");
            }
            if (cateId <= 0 && text.isEmpty()){
                getPresenter().setEffect("");
                getPresenter().setCateId(0);
            }
            getPresenter().onRefresh();
        });
        mIsInit = true;
    }

    @Override
    public void onBackPressed() {
        if (!mFilterPanel.dismissMenu()) super.onBackPressed();
    }
}
