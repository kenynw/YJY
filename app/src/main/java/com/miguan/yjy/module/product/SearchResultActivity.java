package com.miguan.yjy.module.product;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.model.local.SystemPreferences;
import com.miguan.yjy.module.user.FeedbackActivity;
import com.miguan.yjy.utils.LUtils;

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

    @BindView(R.id.sdv_brand_img)
    SimpleDraweeView mSdvBrandImg;

    @BindView(R.id.tv_brand_name)
    TextView mTvBrandName;
    @BindView(R.id.tv_brand_num)
    TextView mTvBrandNum;
    @BindView(R.id.tbtn_product_filter_cate)
    ToggleButton mTbtnProductFilterCate;
    @BindView(R.id.tbtn_product_filter_effect)
    ToggleButton mTbtnProductFilterEffect;
    @BindView(R.id.ll_product_filter)
    LinearLayout mLlProductFilter;
    @BindView(R.id.recycle)
    EasyRecyclerView mRecycle;
    @BindView(R.id.tv_brand_main)
    TextView mTvBrandMain;
    @BindView(R.id.ll_product_include_brand)
    LinearLayout mLlIncludeBrand;

    private SearchFilterPanel mFilterPanel;

    private boolean mIsInit = false;

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mIvBack.setOnClickListener(v -> finish());

        mRecyRecommend.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LUtils.closeKeyboard(mEtKeywords);
                return false;
            }
        });
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
        mTvCount.setText(Html.fromHtml(String.format(getString(R.string.text_search_count), productList.getPageTotal())));
        if (productList.getBrand().getId()== null) {
            mLlIncludeBrand.setVisibility(View.GONE);
        } else {
            mLlIncludeBrand.setVisibility(View.VISIBLE);
            mSdvBrandImg.setImageURI(productList.getBrand().getImg());
            mTvBrandName.setText(productList.getBrand().getName());
            String num = "收录产品数 : <font color=\"#32DAC3\"> " + productList.getBrand().getHot() + " </font>";
            mTvBrandNum.setText(Html.fromHtml(num));
        }


        mTvBrandMain.setOnClickListener(v -> BrandMainPresenter.star(SearchResultActivity.this, productList.getBrand().getId()));
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
                    setBrandLayoutVisibility(View.GONE);
                }
            }
        });

        mEtKeywords.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getPresenter().onRefresh();
                    gotoSearchResult(mEtKeywords.getText().toString().trim());
                    mLlResultFirst.setVisibility(View.VISIBLE);
                    mLlResultSencond.setVisibility(View.GONE);
                    mFilterPanel.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });

        imgSearchCancle.setOnClickListener(v -> clearStr());
        mFilterPanel = new SearchFilterPanel(this, productList.getCategoryList(), productList.getEffectList());
        mFilterPanel.setMenuText(1, cateName);
        mFilterPanel.setOnItemSelectedListener(new SearchFilterPanel.OnItemSelectedListener() {
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

    public void setCateLayoutVisibility(int visibility) {
//        mFilterPanel.setVisibility(visibility);
        mTvCount.setVisibility(visibility);
    }

    public void setBrandLayoutVisibility(int visibility) {
        mLlIncludeBrand.setVisibility(visibility);
        mFilterPanel.setVisibility(visibility);
    }
    private void clearStr() {
        mEtKeywords.setText("");
    }

    public void gotoSearchResult(String name) {
//        SearchResultPresenter.start(SearchResultActivity.this, name, 0, "");
        String oldName = SystemPreferences.getSearchName();
        if (!TextUtils.isEmpty(oldName)) {
            if (!oldName.contains(name))
                SystemPreferences.setSearchName(name + "," + oldName);
        } else {
            SystemPreferences.setSearchName(name + ",");
        }
    }

    @Override
    public void onBackPressed() {
        if (mFilterPanel != null && !mFilterPanel.dismissMenu()) super.onBackPressed();
    }
}
