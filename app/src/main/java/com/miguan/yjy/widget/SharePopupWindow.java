package com.miguan.yjy.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class SharePopupWindow extends PopupWindow implements View.OnClickListener, UMShareListener {

    @BindView(R.id.rl_share_window)
    RelativeLayout mRlShareWindow;

    @BindView(R.id.tv_share_wx_friend)
    TextView mTvWxFirend;

    @BindView(R.id.tv_share_wx_circle)
    TextView mTvWxCircle;

    @BindView(R.id.tv_share_weibo)
    TextView mTvWeibo;

    @BindView(R.id.tv_share_cancel)
    TextView mTvCancel;

    @BindView(R.id.tv_share_title)
    TextView mTvShareTitle;

    private Activity mActivity;

    private Builder mBuilder;

    protected SharePopupWindow(Context context, Builder builder) {
        super(context);
        mActivity = (Activity) context;
        mBuilder = builder;
        init(context);
        initViews();
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.popwindow_share, null);
        ButterKnife.bind(this, view);

        mRlShareWindow.setOnClickListener(v -> dismiss());
        if (!TextUtils.isEmpty(mBuilder.getViewTitle())) mTvShareTitle.setText(mBuilder.getViewTitle());

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0x55000000));
    }

    private void initViews() {
        mTvWxFirend.setOnClickListener(this);
        mTvWxCircle.setOnClickListener(this);
        mTvWeibo.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share_wx_friend:
                UMWeb umWeb = new UMWeb(mBuilder.getUrl());
                umWeb.setTitle(mBuilder.getTitle());
                umWeb.setThumb(mBuilder.getImage());
                umWeb.setDescription(mBuilder.getContent());
                new ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(this)
                        .withMedia(umWeb)
                        .share();
                break;
            case R.id.tv_share_wx_circle:
                UMWeb umWebCircle = new UMWeb(mBuilder.getUrl());
                umWebCircle.setTitle(mBuilder.getTitle());
                umWebCircle.setThumb(mBuilder.getImage());
                new ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(this)
                        .withMedia(umWebCircle)
                        .share();
                break;
            case R.id.tv_share_weibo:
                UMWeb umWebWeibo = new UMWeb(mBuilder.getUrl());
                umWebWeibo.setTitle(mBuilder.getTitle());
                umWebWeibo.setThumb(mBuilder.getImage());
                umWebWeibo.setDescription(mBuilder.getContent());
                new ShareAction(mActivity).setPlatform(SHARE_MEDIA.SINA)
                        .setCallback(this)
                        .withMedia(umWebWeibo)
                        .share();
                break;
            case R.id.tv_share_cancel:
                dismiss();
                break;

        }
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        dismiss();
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        LUtils.toast("分享错误");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        LUtils.toast("取消分享");
    }

    public static class Builder {

        private Context mContext;

        private String mViewTitle;

        private String mUrl;

        private String mTitle;

        private String mContent;

        private UMImage mImage;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setUrl(String url) {
            mUrl = url;
            return this;
        }

        public Builder setViewTitle(String viewTitle) {
            mViewTitle = viewTitle;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setContent(String content) {
            mContent = content;
            return this;
        }

        public UMImage getImage() {
            return mImage;
        }

        public Builder setImage(UMImage image) {
            mImage = image;
            return this;
        }

        public String getViewTitle() {
            return mViewTitle;
        }

        public Context getContext() {
            return mContext;
        }

        public String getUrl() {
            return mUrl;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getContent() {
            return mContent;
        }

        public SharePopupWindow show(View parent) {
            SharePopupWindow popupWindow = new SharePopupWindow(mContext, this);
            popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
            return popupWindow;
        }

    }

}
