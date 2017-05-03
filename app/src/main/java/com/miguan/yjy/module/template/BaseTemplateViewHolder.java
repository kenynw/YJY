package com.miguan.yjy.module.template;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.model.bean.Product;

import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public abstract class BaseTemplateViewHolder extends BaseViewHolder<Product> implements OnImageSelectListener,
        FilterActivity.OnFilterSelectedListener, View.OnClickListener {

    public BaseTemplateViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this, itemView);
    }

    protected void setImageFilter(SimpleDraweeView dv, ImageRequest request) {
        if (request != null) {
            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setTapToRetryEnabled(true)
                    .setOldController(dv.getController())
                    .build();

            dv.setController(controller);
        }
    }

    @Override
    public void onClick(View v) {
        ImageProvider.getInstance((Activity) getContext()).getImageFromCameraOrAlbum(this);
    }

    @Override
    public void onImageSelect() {

    }

    @Override
    public void onImageLoaded(Uri uri) {

    }

    @Override
    public void onError() {

    }

}
