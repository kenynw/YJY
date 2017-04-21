package com.miguan.yjy.module.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @作者 cjh
 * @日期 2016/9/29 10:59
 * @描述 通用的webView
 */
@RequiresPresenter(WebViewPresener.class)
public class WebViewActivity extends ChainBaseActivity {

    // 浏览器
    private WebView webView;

    /**
     * 标题名称
     */
    public static final String REQUEST_NAME_TITLE = "title";
    /**
     * url参数名
     */
    public static final String REQUEST_NAME_URL = "url";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_utils);
        initData();
        initView();
        initWebView();
    }

    public static void satr(Context context, String title,String url) {
        Intent intent = new Intent(context,WebViewActivity.class);
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
        url = bundle.getString(REQUEST_NAME_URL);
        setToolbarTitle(bundle.getString(REQUEST_NAME_TITLE));
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.web_view);

    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


}
