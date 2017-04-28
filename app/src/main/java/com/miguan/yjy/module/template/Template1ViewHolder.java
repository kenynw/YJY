package com.miguan.yjy.module.template;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.jude.library.imageprovider.ImageProvider;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/24. LiaoPeiKun Inc. All rights reserved.
 */

public class Template1ViewHolder extends TemplateViewHolder {

    @BindViews({R.id.dv_template_1_image_0, R.id.dv_template_1_image_1, R.id.dv_template_1_image_2, R.id.dv_template_1_image_3})
    List<SimpleDraweeView> mDvImages;

    @BindViews({R.id.iv_template_1_filter_0, R.id.iv_template_1_filter_1, R.id.iv_template_1_filter_2, R.id.iv_template_1_filter_3})
    List<ImageView> mIvFilters;

    @BindView(R.id.tv_template_0_desc)
    TextView mTvDesc;

    private int mCurPosition;

    private List<Uri> mUris;

    public Template1ViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_template_1);
        mUris = new ArrayList<>();
    }

    @Override
    public void setData(Product data) {
        ButterKnife.apply(mDvImages, new ButterKnife.Action<SimpleDraweeView>() {
            @Override
            public void apply(@NonNull SimpleDraweeView view, int index) {
                view.setOnClickListener(v -> ImageProvider.getInstance((Activity) getContext())
                        .getImageFromCameraOrAlbum(Template1ViewHolder.this));
                mCurPosition = index;
            }
        });

        ButterKnife.apply(mIvFilters, new ButterKnife.Action<ImageView>() {
            @Override
            public void apply(@NonNull ImageView view, int index) {
                view.setOnClickListener(v -> FilterActivity.start((AppCompatActivity) getContext(),
                        mUris.get(index), Template1ViewHolder.this));
            }
        });
    }

    @Override
    public void onImageLoaded(Uri uri) {
        mUris.add(uri);
        mDvImages.get(mCurPosition).setImageURI(uri);
        mIvFilters.get(mCurPosition).setVisibility(View.VISIBLE);
    }

    @Override
    public void onFilterSelected(ImageRequest request) {

    }

//    @Override
//    public void setFilter(Postprocessor processor) {
//
//    }

}
