package com.miguan.yjy.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class SharePopupWindow extends PopupWindow implements View.OnClickListener {

    @BindView(R.id.tv_share_wx_friend)
    TextView mTvWxFirend;

    @BindView(R.id.tv_share_wx_circle)
    TextView mTvWxCircle;

    @BindView(R.id.tv_share_weibo)
    TextView mTvWeibo;

    @BindView(R.id.tv_share_cancel)
    TextView mTvCancel;

    public SharePopupWindow(Context context) {
        super(context);
        init(context);
        initViews();
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.popwindow_share, null);
        ButterKnife.bind(this, view);

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setTouchable(true);
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
                dismiss();
                break;
            case R.id.tv_share_wx_circle:
                dismiss();
                break;
            case R.id.tv_share_weibo:
                dismiss();
                break;
            case R.id.tv_share_cancel:
                dismiss();
                break;

        }
    }
}
