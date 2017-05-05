package com.miguan.yjy.module.template;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
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

    @BindView(R.id.et_template_0_product)
    EditText mEtProduct;

    @BindView(R.id.et_template_0_spec)
    EditText mEtSpec;

    @BindView(R.id.et_template_0_content)
    EditText mEtContent;

    public Template0ViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_template_0);
    }

    @Override
    public void initViews(Product product) {
        mDvImage.setImageURI("res:// /" + R.mipmap.def_image_template);
        mIvFilter.setVisibility(View.GONE);
        mEtProduct.setText(R.string.text_template_0_product);
        mEtSpec.setText(R.string.text_template_0_spec);
        mEtContent.setText(R.string.text_template_0_content);
        mDvImage.setOnClickListener(this);
    }

    @Override
    public void onImageLoaded(Uri uri) {
        mIvFilter.setVisibility(View.VISIBLE);
        mIvFilter.setOnClickListener(v -> FilterActivity.start((AppCompatActivity) getContext(), uri, this));
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(480, 640))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(mDvImage.getController())
                .build();
        mDvImage.setController(controller);
    }

    @Override
    public void hideOperatingViews() {
        super.hideOperatingViews();
        mIvFilter.setVisibility(View.GONE);
        mEtProduct.setCursorVisible(false);
    }

    @Override
    public void onFilterSelected(ImageRequest request, boolean applyAll) {
        setImageFilter(mDvImage, request);
    }

}
