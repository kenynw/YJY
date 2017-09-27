package com.miguan.yjy.module.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.constant.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @作者 cjh
 * @日期 2016/9/29 10:59
 * @描述 通用的webView
 */
@RequiresPresenter(WebViewPresener.class)
public class WebViewActivity extends ChainBaseActivity {

    @BindView(R.id.web_view)
    WebView mWebView;

    /**
     * 标题名称
     */
    public static final String REQUEST_NAME_TITLE = "title";
    /**
     * url参数名
     */
    public static final String REQUEST_NAME_URL = "url";

    private String mUrl;

    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_utils);
        ButterKnife.bind(this);

        initData();
        initWebView();
    }

    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.REQUEST_NAME_TITLE, title);
        intent.putExtra(WebViewActivity.REQUEST_NAME_URL, url);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void initData() {
        if (getIntent().getExtras() == null) {
            finish();
            return;
        }
        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString(REQUEST_NAME_URL);
        mTitle = bundle.getString(REQUEST_NAME_TITLE);
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        // 设置允许加载混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (title.length() > 17) title = title.substring(0, 17) + "...";
                setToolbarTitle(TextUtils.isEmpty(mTitle) ? title : mTitle);
                super.onReceivedTitle(view, title);
            }
        });
        mWebView.loadUrl(getRequestUrl());
        mWebView.addJavascriptInterface(new WebViewOB(this), "android");
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private String getRequestUrl() {
        StringBuilder builder = new StringBuilder(mUrl);
        if (!mUrl.contains("&from=android")) {
            builder.append((mUrl.contains("?") ? "" : "?") + "&from=android");
        }
        if (!mUrl.contains("&version=")) {
            builder.append("&version=" + Constants.API_VERSION);
        }

        return new String(builder);
    }

}
