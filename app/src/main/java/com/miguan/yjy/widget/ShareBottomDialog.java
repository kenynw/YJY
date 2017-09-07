package com.miguan.yjy.widget;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.utils.LUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.TextUtils.isEmpty;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class ShareBottomDialog extends BottomSheetDialog implements View.OnClickListener, UMShareListener {

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

    protected ShareBottomDialog(Context context, Builder builder) {
        super(context);
        mActivity = (Activity) context;
        mBuilder = builder;
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.dialog_bottom_share, null);
        ButterKnife.bind(this, view);

        if (!isEmpty(mBuilder.getViewTitle())) mTvShareTitle.setText(mBuilder.getViewTitle());

        setContentView(view);
        initViews();
    }

    private void initViews() {
        mTvWxFirend.setOnClickListener(this);
        mTvWxCircle.setOnClickListener(this);
        mTvWeibo.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        UMImage image = TextUtils.isEmpty(mBuilder.getImage()) ? new UMImage(mActivity, R.mipmap.ic_launcher) : new UMImage(mActivity, mBuilder.getImage());

        switch (v.getId()) {
            case R.id.tv_share_wx_friend:
                if (isInstall(SHARE_MEDIA.WEIXIN)) {
                    UMWeb umWeb = new UMWeb(mBuilder.getUrl());
                    umWeb.setTitle(mBuilder.getTitle());
                    umWeb.setThumb(image);
                    umWeb.setDescription(mBuilder.getContent());
                    new ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN)
                            .setCallback(this)
                            .withMedia(umWeb)
                            .share();
                } else {
                    LUtils.toast("未安装微信");
                    dismiss();
                }

                break;
            case R.id.tv_share_wx_circle:
                if (isInstall(SHARE_MEDIA.WEIXIN)) {
                    UMWeb umWebCircle = new UMWeb(mBuilder.getUrl());
                    umWebCircle.setTitle(mBuilder.getWxCircleTitle());
                    umWebCircle.setThumb(image);
                    new ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .setCallback(this)
                            .withMedia(umWebCircle)
                            .share();
                } else {
                    LUtils.toast("未安装微信");
                    dismiss();
                }
                break;
            case R.id.tv_share_weibo:
                if (isInstall(SHARE_MEDIA.SINA)) {
                    new ShareAction(mActivity).setPlatform(SHARE_MEDIA.SINA)
                            .setCallback(this)
                            .withText(mBuilder.getWbContent())
                            .withMedia(image)
                            .share();
                } else {
                    LUtils.toast("未安装微博");
                    dismiss();
                }
                break;
            case R.id.tv_share_cancel:
                dismiss();
                break;

        }
    }

    private boolean isInstall(SHARE_MEDIA share_media) {
        return UMShareAPI.get(mBuilder.getContext()).isInstall((Activity) mBuilder.getContext(), share_media);
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        dismiss();
        if (mBuilder.getId() > 0 && mBuilder.getType() > 0) {
            CommonModel.getInstance().analyticsShare(mBuilder.getId(), mBuilder.getType())
                    .subscribe();
        }
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        CommonModel.getInstance().analyticsShare(-1, 3).subscribe();
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        LUtils.log("分享错误" + throwable.getMessage());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        LUtils.log("取消分享");
    }

    public static class Builder {

        private Context mContext;

        private String mViewTitle;

        private String mUrl;

        private String mTitle;

        private String mContent;

        private String mImageUrl;

        private String mWxCircleTitle;

        private String mWbContent;

        private int mId; // 分享的id

        private int mType; // 1为产品，2为文章

        public String getWxCircleTitle() {
            return mWxCircleTitle;
        }

        public Builder setWxCircleTitle(String wxCircleTitle) {
            mWxCircleTitle = wxCircleTitle;
            return this;
        }

        public String getWbContent() {
            return mWbContent;
        }

        public Builder setWbContent(String wbContent) {
            mWbContent = wbContent;
            return this;
        }

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

        public int getId() {
            return mId;
        }

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public int getType() {
            return mType;
        }

        public Builder setType(int type) {
            mType = type;
            return this;
        }

        public String getImage() {
            return mImageUrl;
        }

        public Builder setImageUrl(String image) {
            mImageUrl = image;
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

        public ShareBottomDialog show() {
            ShareBottomDialog popupWindow = new ShareBottomDialog(mContext, this);
            popupWindow.show();
            return popupWindow;
        }

    }

}
