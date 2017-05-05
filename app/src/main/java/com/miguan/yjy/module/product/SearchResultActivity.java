package com.miguan.yjy.module.product;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
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
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.module.user.FeedbackActivity;
import com.miguan.yjy.utils.LUtils;

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

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.et_product_keywords)
    EditText mEtKeywords;

    @BindView(R.id.iv_product_search_clear)
    ImageView imgSearchCancle;

    @BindView(R.id.tv_product_list_count)
    TextView mTvCount;

    @BindView(R.id.ll_product_result_first)
    LinearLayout mLlResultFirst;

    @BindView(R.id.recy_product_recommend)
    EasyRecyclerView mRecyRecommend;

    @BindView(R.id.ll_product_result_sencond)
    LinearLayout mLlResultSencond;

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
//        mTvCount.setText(String.format(getString(R.string.text_search_count), productList.getPageTotal()));
        String money = "共为您找到<font color=\"#32DAC3\"> " + productList.getPageTotal()+ " </font>款产品";
        mTvCount.setText(Html.fromHtml(money));
        if (mIsInit) return;
        mEtKeywords.setText(keywords);
        if (TextUtils.isEmpty(keywords)) {
            imgSearchCancle.setVisibility(View.GONE);
        } else {
            imgSearchCancle.setVisibility(View.VISIBLE);
        }
        mEtKeywords.setSelection(keywords.length());
        mEtKeywords.requestFocus();
        mEtKeywords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    imgSearchCancle.setVisibility(View.GONE);
                    mLlResultFirst.setVisibility(View.VISIBLE);
                    mLlResultSencond.setVisibility(View.GONE);
                } else {
                    imgSearchCancle.setVisibility(View.VISIBLE);
                    mLlResultFirst.setVisibility(View.GONE);
                    getPresenter().setRecommendData(s.toString());
//                    getPresenter().onRefresh();
                }
            }
        });

        mEtKeywords.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getPresenter().onRefresh();
                    mLlResultFirst.setVisibility(View.VISIBLE);
                    mLlResultSencond.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });

        imgSearchCancle.setOnClickListener(v -> clearStr());
        mFilterPanel = new FilterPanel(this, productList.getCategroy(), productList.getEffects());
        mFilterPanel.setMenuText(1, cateName);
        mFilterPanel.setOnItemSelectedListener(new FilterPanel.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int cateId, String text) {
                mFilterPanel.dismissMenu();
                if (cateId > 0) {
                    getPresenter().setCateId(cateId);
                }
                if (!TextUtils.isEmpty(text)) {
                    getPresenter().setEffect(text);
                    getPresenter().setType(2);
                }
                if (cateId <= 0 && text.isEmpty()) {
                    getPresenter().setEffect("");
                    getPresenter().setCateId(0);
                }
                getPresenter().onRefresh();
            }

            @Override
            public void onMenuCheck() {
                LUtils.closeKeyboard(mEtKeywords);
            }
        });
        mIsInit = true;
    }

    private void initListener(List<Product> datas) {
//        mEtKeywords.setHint(productNum);
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tvCancel.getText().toString().equals("取消")) {
//                    finish();
//                } else {
//                    gotoSearchResult(edtSearch.getText().toString().trim());
//                }
//            }
//        });

    }

    public void setCateLayoutVisibility(int visibility) {
        mFilterPanel.setVisibility(visibility);
        mTvCount.setVisibility(visibility);
    }

    private void clearStr() {
        mEtKeywords.setText("");
    }

    @Override
    public void onBackPressed() {
        if (mFilterPanel != null && !mFilterPanel.dismissMenu()) super.onBackPressed();
    }
}
