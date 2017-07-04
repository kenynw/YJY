package com.miguan.yjy.module.article;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.module.common.WebViewOB;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/30. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleDetailHeader implements RecyclerArrayAdapter.ItemView {

    @BindView(R.id.wv_article_detail)
    WebView mWebView;

    @BindView(R.id.tv_article_evaluate_num)
    TextView mTvEvaluateNum;

    private Context mContext;

    public ArticleDetailHeader(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_article_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBindView(View headerView) {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
//        mSettings.setBlockNetworkImage(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //往浏览器标识字符串里添加自定义的字符串，用于服务器判断是否为客户端
        if (!settings.getUserAgentString().contains("android")) {
            settings.setUserAgentString(settings.getUserAgentString() + "android");
        }

        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);

        mWebView.addJavascriptInterface(new WebViewOB(mContext), "Android");
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                mSettings.setBlockNetworkImage(false);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

        });
    }

    public void loadUrl(Article article) {
        if (TextUtils.isEmpty(mWebView.getUrl())) {
            mWebView.loadUrl(article.getLinkUrl());
            mTvEvaluateNum.setText(String.format(mContext.getString(R.string.text_article_evaluate), article.getComment_num()));
        }
    }

}
