package com.miguan.yjy.module.template;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import butterknife.BindView;

/**
 * Copyright (c) 2017/5/2. LiaoPeiKun Inc. All rights reserved.
 */

public class Template2ViewHolder extends BaseTemplateViewHolder {

    @BindView(R.id.dv_template_2_image)
    SimpleDraweeView mDvImage;

    @BindView(R.id.iv_template_2_filter)
    ImageView mIvFilter;

    @BindView(R.id.fl_template_2_image)
    FrameLayout mFlImage;

    @BindView(R.id.et_template_2_title)
    EditText mEtTitle;

    @BindView(R.id.et_template_2_content)
    EditText mEtContent;

    private Uri mUri;

    public Template2ViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_template_2);
    }

    @Override
    public void initViews(Product product) {
        mDvImage.setOnClickListener(this);
        mDvImage.setImageURI("res:// /" + R.mipmap.def_image_template);
        mIvFilter.setOnClickListener(v -> FilterActivity.start((AppCompatActivity) getContext(),
                mUri, Template2ViewHolder.this));
        mIvFilter.setVisibility(View.GONE);
        mEtTitle.setText(R.string.text_template_2_title);
        mEtContent.setText(R.string.text_template_2_content);
    }

    @Override
    public void onFilterSelected(ImageRequest request, boolean applyAll) {
        setImageFilter(mDvImage, request);
    }

    @Override
    public void onImageLoaded(Uri uri) {
        mDvImage.setImageURI(uri);
        mIvFilter.setVisibility(View.VISIBLE);
        mUri = uri;
    }

    @Override
    public void hideOperatingViews() {
        super.hideOperatingViews();
        mIvFilter.setVisibility(View.GONE);
        mDvImage.setFocusable(true);
        mDvImage.setFocusableInTouchMode(true);
    }

}
