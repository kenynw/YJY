package com.miguan.yjy.module.template;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(FilterActivityPresenter.class)
public class FilterActivity extends ChainBaseActivity<FilterActivityPresenter> implements
        FilterAdapter.OnItemClickListener, View.OnClickListener {

    public static final String EXTRA_POSITION = "position";

    public static final String EXTRA_APPLY_ALL = "apply_all";

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

    private static OnFilterSelectedListener sListener;

    private int mRotation;

    private Bitmap mBitmap;

    private int mCurPosition = 0;

    public static void start(AppCompatActivity activity, String path, boolean isCircle, OnFilterSelectedListener listener) {
        sListener = listener;
        Intent intent = new Intent(activity, FilterActivity.class);
        intent.putExtra(EXTRA_FILTER_URI, path);
        intent.putExtra(EXTRA_IS_CIRCLE, isCircle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_filter);
        ButterKnife.bind(this);

        String uri = getIntent().getStringExtra(EXTRA_FILTER_URI);
        boolean isCircle = getIntent().getBooleanExtra(EXTRA_IS_CIRCLE, false);

        mIvCropImage.setFocusWidth(LUtils.getScreenWidth());
        mIvCropImage.setFocusHeight(LUtils.getScreenWidth());
        mIvCropImage.setFocusStyle(isCircle ? CropImageView.Style.CIRCLE : CropImageView.Style.RECTANGLE);

        Glide.with(this)
                .load(Uri.parse(uri))
                .asBitmap()
                .override(200, 200)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mBitmap = resource;
                        mIvCropImage.setImageBitmap(resource);
                    }
                });

        mIvCancel.setOnClickListener(this);
        mIvrOk.setOnClickListener(this);

        mIvRotation.setOnClickListener(v -> {
            mRotation = mRotation >= 270 ? 0 : mRotation + 90;
            mIvCropImage.rotate90();
        });

        mGridFilters.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mGridFilters.setHasFixedSize(true);
        mGridFilters.addItemDecoration(getDecoration());

        FilterAdapter adapter = new FilterAdapter(this);
        adapter.setOnFilterSelectedListener(this);
        mGridFilters.setAdapter(adapter);
    }

    private SpaceDecoration getDecoration() {
        SpaceDecoration spaceDecoration = new SpaceDecoration(LUtils.dp2px(10));
        spaceDecoration.setPaddingStart(false);
        spaceDecoration.setPaddingEdgeSide(false);
        return spaceDecoration;
    }

    @Override
    public void onItemClick(int position) {
        if (mBitmap != null && mCurPosition != position){
            Bitmap bitmap = null;
            try {
                GPUImage gpuImage = new GPUImage(FilterActivity.this);
                gpuImage.setFilter(getPresenter().getFilter(position));
                gpuImage.setImage(mBitmap);
                bitmap = gpuImage.getBitmapWithFilterApplied();
                mIvCropImage.setImageBitmap(bitmap);
                gpuImage.deleteImage();
            } catch (OutOfMemoryError error) {
                error.printStackTrace();
                LUtils.toast("内存不够用啦~");
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
            mCurPosition = position;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_filter_cancel) {
            finish();
        } else {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File mediaStorageDirectory = new File(path, "YJY/");
            mIvCropImage.saveBitmapToFile(mediaStorageDirectory, 800, 800, false);
            mIvCropImage.setOnBitmapSaveCompleteListener(new CropImageView.OnBitmapSaveCompleteListener() {
                @Override
                public void onBitmapSaveSuccess(File file) {
                    if (sListener != null) sListener.onFilterSelected(file, mCbApplyAll.isChecked());

                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_APPLY_ALL, mCbApplyAll.isChecked());
                    intent.putExtra(EXTRA_POSITION, mCurPosition);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onBitmapSaveError(File file) {

                }
            });
        }
    }

    public interface OnFilterSelectedListener {
        void onFilterSelected(File file, boolean applyAll);
    }

}
