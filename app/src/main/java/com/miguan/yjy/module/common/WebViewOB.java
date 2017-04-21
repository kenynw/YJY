package com.miguan.yjy.module.common;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.main.MainActivity;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.utils.LUtils;


/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class WebViewOB {

    private Context mContext;

    public WebViewOB(Context context) {
        mContext = context;
    }

    /**
     * 显示Toast消息提示
     * @param message
     */
    @JavascriptInterface
    public void showToast(String message) {
        LUtils.toast(message);
    }

    /**
     * 获取缓存的用户ID
     * @return
     */
    @JavascriptInterface
    public int getUserId() {
        return UserPreferences.getUserID();
    }

    /**
     * 打开登录页面
     */
    @JavascriptInterface
    public void toLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

    /**
     * 打开产品详情
     * @param productId 产品ID
     */
    @JavascriptInterface
    public void toProductDetail(int productId) {
        ProductDetailPresenter.start(mContext, productId);
    }

    /**
     * 返回首页
     */
    @JavascriptInterface
    public void toHome() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

}