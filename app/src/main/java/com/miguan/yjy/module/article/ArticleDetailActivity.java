package com.miguan.yjy.module.article;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.module.common.WebViewOB;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ArticleDetailPresenter.class)
public class ArticleDetailActivity extends BaseDataActivity<ArticleDetailPresenter, Article> implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.fl_article_detail_star)
    LinearLayout mFlStar;

    @BindView(R.id.fl_article_detail_comment)
    FrameLayout mFlComment;

    @BindView(R.id.wv_article_detail)
    WebView mWebView;

    @BindView(R.id.recycle)
    RecyclerView mRecycle;

    @BindView(R.id.iv_article_detail_star)
    ImageView mIvStar;

    @BindView(R.id.tv_product_user_evaluate_num)
    TextView mTvEvaluateNum;

    @BindView(R.id.tv_evaluate_empty)
    TextView mTvEmpty;

    @BindView(R.id.ll_article_detail)
    LinearLayout mLlArticleDetail;

    private boolean mIsStar;

    private WebSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_article_detail);
        setToolbarTitle(R.string.text_article_detail);
        ButterKnife.bind(this);

        mFlStar.setOnClickListener(v -> getPresenter().star(mIsStar));

        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration decoration = new DividerDecoration(0xFFEBEBEB, LUtils.dp2px(1), LUtils.dp2px(78), LUtils.dp2px(15));
        decoration.setDrawLastItem(false);
        mRecycle.addItemDecoration(decoration);

        mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setDomStorageEnabled(true);
        mSettings.setDefaultTextEncodingName("utf-8");
//        mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //往浏览器标识字符串里添加自定义的字符串，用于服务器判断是否为客户端
        if (!mSettings.getUserAgentString().contains("android")) {
            mSettings.setUserAgentString(mSettings.getUserAgentString() + "android");
        }

        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);

        mWebView.addJavascriptInterface(new WebViewOB(this), "Android");
    }

    @Override
    public void setData(Article article) {
        mWebView.loadUrl(article.getLinkUrl());
        mTvEvaluateNum.setText(String.format(getString(R.string.text_article_evaluate), article.getComment_num()));
        setStar(article.getIsGras() == 1);
        mFlComment.setOnClickListener(v -> AddEvaluatePresenter.start(this, article.getId(), 2, 0));

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLlArticleDetail.setVisibility(View.VISIBLE);
                RecyclerArrayAdapter<Evaluate> adapter = getPresenter().getAdapter();
                mRecycle.setAdapter(adapter);
                if (article.getCommentList() != null && article.getCommentList().size() > 0) {
                    mTvEmpty.setVisibility(View.GONE);
                    adapter.clear();
                    adapter.addAll(article.getCommentList());
                } else {
                    mRecycle.setVisibility(View.GONE);
                }

                getExpansionDelegate().hideProgressBar();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("article/")) {
                    String id = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
                    ArticleDetailPresenter.start(ArticleDetailActivity.this, Integer.valueOf(id));
                    return true;
                }

                WebViewActivity.start(ArticleDetailActivity.this, "", url);
                return true;
            }

        });
    }

    public void setStar(boolean isStar) {
        mIsStar = isStar;
        mIvStar.setImageResource(isStar ? R.mipmap.ic_article_star_pressed : R.mipmap.ic_article_star_normal);
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
