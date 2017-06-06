package com.miguan.yjy.module.template;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.CropImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(FilterActivityPresenter.class)
public class FilterActivity extends ChainBaseActivity<FilterActivityPresenter> implements FilterAdapter.OnFilterSelectedListener {

    public static final String EXTRA_FILTER_URI = "filter_uri";

    public static final String EXTRA_IS_CIRCLE = "is_circle";

    @BindView(R.id.iv_filter_thumb)
    CropImageView mIvCropImage;

    @BindView(R.id.iv_image_edit_rotation)
    ImageView mIvRotation;

    @BindView(R.id.cb_filter_apply_all)
    CheckBox mCbApplyAll;

    @BindView(R.id.grid_filter_filters)
    RecyclerView mGridFilters;

    @BindView(R.id.iv_filter_cancel)
    ImageView mIvCancel;

    @BindView(R.id.iv_filter_ok)
    ImageView mIvrOk;

    private static OnFilterSelectedListener sFilterSelectedListener;

    private int mRotation;

    private Bitmap mBitmap;

    public static void start(AppCompatActivity activity, Uri uri, boolean isCircle, OnFilterSelectedListener listener) {
        sFilterSelectedListener = listener;
        Intent intent = new Intent(activity, FilterActivity.class);
        intent.putExtra(EXTRA_FILTER_URI, uri);
        intent.putExtra(EXTRA_IS_CIRCLE, isCircle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_filter);
        ButterKnife.bind(this);

        Uri uri = getIntent().getParcelableExtra(EXTRA_FILTER_URI);
        boolean isCircle = getIntent().getBooleanExtra(EXTRA_IS_CIRCLE, false);

        Glide.with(this)
                .load(uri)
                .asBitmap()
                .fitCenter()
                .override(768, 768)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mBitmap = resource;
                        mIvCropImage.setImageBitmap(resource);
                    }
                });

        mIvCropImage.setFocusWidth(LUtils.getScreenWidth());
        mIvCropImage.setFocusStyle(isCircle ? CropImageView.Style.CIRCLE : CropImageView.Style.RECTANGLE);

        mIvCancel.setOnClickListener(v -> finish());
        mIvrOk.setOnClickListener(v -> {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File mediaStorageDirectory = new File(path, "YJY/");
            mIvCropImage.saveBitmapToFile(mediaStorageDirectory, 800, 800, false);
            mIvCropImage.setOnBitmapSaveCompleteListener(new CropImageView.OnBitmapSaveCompleteListener() {
                @Override
                public void onBitmapSaveSuccess(File file) {
                    if (sFilterSelectedListener != null)
                        sFilterSelectedListener.onFilterSelected(file, mCbApplyAll.isChecked());
                }

                @Override
                public void onBitmapSaveError(File file) {

                }
            });
            finish();
        });

        mIvRotation.setOnClickListener(v -> {
            mRotation = mRotation >= 270 ? 0 : mRotation + 90;
            mIvCropImage.rotate90();
        });

        mGridFilters.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mGridFilters.setHasFixedSize(true);
        mGridFilters.addItemDecoration(getDecoration());
        mGridFilters.setAdapter(getPresenter().createAdapter());
    }

    private SpaceDecoration getDecoration() {
        SpaceDecoration spaceDecoration = new SpaceDecoration(LUtils.dp2px(10));
        spaceDecoration.setPaddingStart(false);
        spaceDecoration.setPaddingEdgeSide(false);
        return spaceDecoration;
    }

    @Override
    public void onFilterSelected(GPUImageFilter filter) {
        GPUImage gpuImage = new GPUImage(FilterActivity.this);
        gpuImage.setImage(mBitmap);
        gpuImage.setFilter(filter);
        Bitmap bitmap = gpuImage.getBitmapWithFilterApplied();
        mIvCropImage.setImageBitmap(bitmap);
    }

    public interface OnFilterSelectedListener {
        void onFilterSelected(File file, boolean applyAll);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBitmap.recycle();
        mBitmap = null;
    }
}
