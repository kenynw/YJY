package com.miguan.yjy.module.template;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/5/18. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplateViewPanel {

    @BindView(R.id.fl_template_delete)
    FrameLayout mFlDelete;

    private Activity mActivity;

    public final View mItemView;

    public TemplateViewPanel(Activity activity, ViewGroup parent, @LayoutRes int res) {
        mActivity = activity;
        mItemView = LayoutInflater.from(activity).inflate(res, parent);
        ButterKnife.bind(this, mItemView);

        mFlDelete.setOnClickListener(parent::removeView);
    }

    public interface OnDeleteListener {
        void onDelete(View view);
    }

}
