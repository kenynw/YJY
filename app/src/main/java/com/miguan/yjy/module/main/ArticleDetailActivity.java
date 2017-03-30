package com.miguan.yjy.module.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.EvaluateViewHolder;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ArticleDetailPresenter.class)
public class ArticleDetailActivity extends BaseListActivity<ArticleDetailPresenter> {

    @BindView(R.id.fl_article_detail_star)
    FrameLayout mFlStar;

    @BindView(R.id.fl_article_detail_comment)
    FrameLayout mFlComment;

    @BindView(R.id.wv_article_detail)
    WebView mWvDetail;

    @BindView(R.id.recycle)
    EasyRecyclerView mRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarTitle("这是品牌名");
        mFlStar.setOnClickListener(v -> getPresenter().star());
        mFlComment.setOnClickListener(v -> ArticleCommentPresenter.start(this, 0));

        mWvDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (!TextUtils.isEmpty(url) && Uri.parse(url).getScheme() != null) {
//                    String scheme = Uri.parse(url).getScheme();
//                    if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
//                        String act = Uri.parse(url).getQueryParameter("act");
//                        int mid = Integer.valueOf(Uri.parse(url).getQueryParameter("mid"));
//                        if (act.equalsIgnoreCase("member_sns_home")) {
//                            Intent intent = new Intent(ArticleDetailActivity.this, ArticleDetailActivity.class);
//                            intent.putExtra("user_id", mid);
//                            startActivity(intent);
//                            return true;
//                        }
//                    }
//                }

                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    return true;
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWvDetail.loadUrl("http://m.yjyapp.com/article/index?id=72");
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setContainerLayoutRes(R.layout.main_activity_article_detail).setRefreshAble(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LUtils.toast("分享中...");
        return super.onOptionsItemSelected(item);
    }
}
