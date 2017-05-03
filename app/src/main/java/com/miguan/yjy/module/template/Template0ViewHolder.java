package com.miguan.yjy.module.template;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.jude.library.imageprovider.ImageProvider;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import butterknife.BindView;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class Template0ViewHolder extends BaseTemplateViewHolder {

    @BindView(R.id.fl_template_0_image)
    FrameLayout mFlImage;

    @BindView(R.id.dv_template_0_image)
    SimpleDraweeView mDvImage;

    @BindView(R.id.iv_template_0_filter)
    ImageView mIvFilter;

    public Template0ViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_template_0);
    }

    @Override
    public void setData(Product data) {
        mDvImage.setOnClickListener(v -> ImageProvider.getInstance((Activity) getContext()).getImageFromCameraOrAlbum(this));
    }

    @Override
    public void onImageLoaded(Uri uri) {
        mIvFilter.setVisibility(View.VISIBLE);
        mIvFilter.setOnClickListener(v -> FilterActivity.start((AppCompatActivity) getContext(), uri, this));
        mDvImage.setImageURI(uri);
    }

    @Override
    public void onFilterSelected(ImageRequest request, boolean applyAll) {
        setImageFilter(mDvImage, request);
    }

}
