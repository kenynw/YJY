package com.miguan.yjy.module.article;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.module.common.WebViewOB;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ArticleDetailPresenter.class)
public class ArticleDetailActivity extends BaseListActivity<ArticleDetailPresenter> implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.fl_article_detail_star)
    LinearLayout mFlStar;

    @BindView(R.id.fl_article_detail_comment)
    FrameLayout mFlComment;

    @BindView(R.id.wv_article_detail)
    WebView mWebView;

    @BindView(R.id.recycle)
    EasyRecyclerView mRecycle;

    @BindView(R.id.iv_article_detail_star)
    ImageView mIvStar;

    @BindView(R.id.tv_product_user_evaluate_num)
    TextView mTvEvaluateNum;

    private boolean mIsStar;

    private WebSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_article_detail);
        ButterKnife.bind(this);

        getExpansionDelegate().showProgressBar();

        mFlStar.setOnClickListener(v -> getPresenter().star(mIsStar));

        mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setBlockNetworkImage(true);
        mSettings.setDomStorageEnabled(true);
        mSettings.setDefaultTextEncodingName("utf-8");
        mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //往浏览器标识字符串里添加自定义的字符串，用于服务器判断是否为客户端
        if (!mSettings.getUserAgentString().contains("android")) {
            mSettings.setUserAgentString(mSettings.getUserAgentString() + "android");
        }

        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);

        mWebView.addJavascriptInterface(new WebViewOB(this), "Android");
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSettings.setBlockNetworkImage(false);
                getExpansionDelegate().hideProgressBar();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

        });
    }

    public void setData(Article article) {
        mWebView.loadUrl(article.getLinkUrl());
        mTvEvaluateNum.setText(String.format(getString(R.string.text_article_evaluate), article.getComment_num()));
        setStar(article.getIsGras() == 1);
        mFlComment.setOnClickListener(v -> AddEvaluatePresenter.start(this, article.getId(), 2, 0));
    }

    public void setStar(boolean isStar) {
        mIsStar = isStar;
        mIvStar.setImageResource(isStar ? R.mipmap.ic_article_star_pressed : R.mipmap.ic_article_star_normal);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig()
                .setContainerEmptyRes(R.layout.empty_evaluate_list)
                .hasItemDecoration(false)
                .setContainerLayoutRes(R.layout.main_activity_article_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getPresenter().share();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

    }

}