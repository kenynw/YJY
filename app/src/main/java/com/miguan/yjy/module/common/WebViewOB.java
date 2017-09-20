package com.miguan.yjy.module.common;

import android.content.Intent;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miguan.yjy.R;
import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.module.test.TestGuideActivity;
import com.miguan.yjy.module.test.TestInitActivity;
import com.miguan.yjy.module.test.TestResultActivity;
import com.miguan.yjy.utils.LUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
@SuppressWarnings("unused")
public class WebViewOB implements UMShareListener {

    public static String EXTRA_WEBVIEW_TAG = "webViewTag";

    private ChainBaseActivity mContext;

    public WebViewOB(ChainBaseActivity context) {
        mContext = context;
    }

    /**
     * 显示Toast消息提示
     *
     * @param message
     */
    @JavascriptInterface
    public void showToast(String message) {
        LUtils.toast(message);
    }

    /**
     * 获取缓存的用户ID
     *
     * @return
     */
    @JavascriptInterface
    public int getUserId() {
        return UserPreferences.getUserID();
    }

    /**
     * 获取登录令牌
     *
     * @return
     */
    @JavascriptInterface
    public String getUserToken() {
        return UserPreferences.getToken();
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
     *
     * @param productId 产品ID
     */
    @JavascriptInterface
    public void toProductDetail(int productId) {
        ProductDetailPresenter.start(mContext, productId);
    }

    /**
     * 肤质测试完成
     */
    @JavascriptInterface
    public void toHome(String skins) {
        LUtils.log("skins: " + skins);
        List<Skin> skin = new Gson().fromJson(skins, new TypeToken<List<Skin>>() {}.getType());
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < skin.size(); i++) {
            if (!TextUtils.isEmpty(skin.get(i).getLetter())) {
                nums.add(i);
            }
        }

        Intent intent;
        if (nums.size() == 4) {
            intent = new Intent(mContext, TestResultActivity.class);
            UserPreferences.setIsShowTest(true);
        } else {
            intent = new Intent(mContext, TestInitActivity.class);
            UserPreferences.setIsShowTest(false);
        }
        intent.putExtra(EXTRA_WEBVIEW_TAG, 1);
        mContext.startActivity(intent);
        if (TestInitActivity.testInitActivity != null) {
            TestInitActivity.testInitActivity.finish();
        }

        if (TestGuideActivity.testGuideActivity != null) {
            TestGuideActivity.testGuideActivity.finish();
        }
        mContext.finish();
        EventBus.getDefault().post(skin);
    }

    /**
     * 分享
     *
     * @param type
     * @param title
     * @param content
     * @param iconUrl
     * @param url
     */
    @JavascriptInterface
    public void shareImage(int type, String title, String content, String iconUrl, String url) {
        if (type == 0 || type == 1) {
            if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                LUtils.toast("未安装微信客户端");
                return;
            }
        }

        mContext.getExpansionDelegate().showProgressBar("正在等待授权");

        switch (type) {
            case 0:
                UMWeb umWeb = new UMWeb(url);
                umWeb.setTitle(title);
                umWeb.setThumb(new UMImage(mContext, iconUrl));
                umWeb.setDescription(content);
                new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(umWeb)
                        .setCallback(this)
                        .share();
                break;
            case 1:
                UMWeb umWebCircle = new UMWeb(url);
                umWebCircle.setTitle(title);
                umWebCircle.setThumb(new UMImage(mContext, iconUrl));
                new ShareAction(mContext).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(umWebCircle)
                        .setCallback(this)
                        .share();
                break;
            case 2:
                new ShareAction(mContext).setPlatform(SHARE_MEDIA.SINA)
                        .withText(content)
                        .withMedia(new UMImage(mContext, iconUrl))
                        .setCallback(this)
                        .share();
                break;
        }
    }

    /**
     * 分享图片
     *
     * @param type     分享平台 0-微信好友 1-朋友圈 2-新浪
     * @param imageUrl 图片Url
     */
    @JavascriptInterface
    public void shareImage(int type, String imageUrl) {
        if (type == 0 || type == 1) {
            if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                LUtils.toast("未安装微信客户端");
                return;
            }
        }

        mContext.getExpansionDelegate().showProgressBar("正在等待授权");

        UMImage image = new UMImage(mContext, imageUrl);
        image.compressStyle = UMImage.CompressStyle.QUALITY;

        ShareAction action = new ShareAction(mContext)
                .setCallback(this)
                .withMedia(image);
        switch (type) {
            case 0:
                action.setPlatform(SHARE_MEDIA.WEIXIN);
                break;
            case 1:
                action.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case 2:
                action.setPlatform(SHARE_MEDIA.SINA);
                break;
        }
        action.share();
    }

    /**
     * 下载安装包
     *
     * @param url apk
     */
    @JavascriptInterface
    public void startDownloadApk(String url) {
        LUtils.toast("开始下载安装包");
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.putExtra("title", "正在下载" + mContext.getString(R.string.app_name));
        intent.putExtra("url", url);
        intent.putExtra("name", "YJY" + System.currentTimeMillis() + ".apk");
        mContext.startService(intent);
    }

    @JavascriptInterface
    public void checkUpdate() {
        CommonModel.getInstance().checkUpdate(mContext);
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        mContext.getExpansionDelegate().hideProgressBar();
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        mContext.getExpansionDelegate().hideProgressBar();
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        mContext.getExpansionDelegate().hideProgressBar();
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        mContext.getExpansionDelegate().hideProgressBar();
    }

}
