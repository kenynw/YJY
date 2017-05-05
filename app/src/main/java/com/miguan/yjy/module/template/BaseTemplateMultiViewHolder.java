package com.miguan.yjy.module.template;

import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/24. LiaoPeiKun Inc. All rights reserved.
 */

public abstract class BaseTemplateMultiViewHolder extends BaseTemplateViewHolder {

    protected List<SimpleDraweeView> mDvImages;

    private List<ImageView> mIvFilters;

    private SparseArray<Uri> mUris;

    private int mCurPosition;

    public BaseTemplateMultiViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        mDvImages = new ArrayList<>();
        mIvFilters = new ArrayList<>();
        mUris = new SparseArray<>();

        if (getImageResIds().length > 0) {
            for (@IdRes int imageRes : getImageResIds()) {
                mDvImages.add(ButterKnife.findById(itemView, imageRes));
            }
        }

        if (getFilterResIds().length > 0) {
            for (@IdRes int filterRes : getFilterResIds()) {
                mIvFilters.add(ButterKnife.findById(itemView, filterRes));
            }
        }
    }

    public abstract @IdRes int[] getImageResIds();

    public abstract @IdRes int[] getFilterResIds();

    @Override
    public void setData(Product data) {
        super.setData(data);
        ButterKnife.apply(mDvImages, new ButterKnife.Action<SimpleDraweeView>() {
            @Override
            public void apply(@NonNull SimpleDraweeView view, int index) {
                view.setOnClickListener(v -> {
                    BaseTemplateMultiViewHolder.this.onClick(v);
                    mCurPosition = index;
                });
            }
        });

        ButterKnife.apply(mIvFilters, new ButterKnife.Action<ImageView>() {
            @Override
            public void apply(@NonNull ImageView view, int index) {
                view.setOnClickListener(v -> {
                    FilterActivity.start((AppCompatActivity) getContext(),
                            mUris.get(index), BaseTemplateMultiViewHolder.this);
                    mCurPosition = index;
                });
            }
        });
    }

    @Override
    public void initViews(Product product) {
        for (SimpleDraweeView dvImage : mDvImages) {
            dvImage.setImageURI("res:// /" + R.mipmap.def_image_template);
        }
        for (ImageView ivFilter : mIvFilters) {
            ivFilter.setVisibility(View.GONE);
        }
    }

    @Override
    public void onImageLoaded(Uri uri) {
        mUris.put(mCurPosition, uri);
        mDvImages.get(mCurPosition).setImageURI(uri);
        mIvFilters.get(mCurPosition).setVisibility(View.VISIBLE);
    }

    @Override
    public void onFilterSelected(ImageRequest request, boolean applyAll) {
        if (applyAll) {
            for (SimpleDraweeView dvImage : mDvImages) {
                setImageFilter(dvImage, request);
            }
        } else {
            setImageFilter(mDvImages.get(mCurPosition), request);
        }
    }

    @Override
    public void hideOperatingViews() {
        super.hideOperatingViews();
        for (ImageView ivFilter : mIvFilters) {
            ivFilter.setVisibility(View.GONE);
        }
        mIvFilters.get(0).setFocusableInTouchMode(true);
        mIvFilters.get(0).setFocusable(true);
    }
}
