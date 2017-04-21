package com.miguan.yjy.module.template;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.module.template.FilterAdapter.FilterType;
import com.miguan.yjy.utils.LUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */

public class FilterActivity extends AppCompatActivity implements GPUImageView.OnPictureSavedListener {

    public static final String EXTRA_FILTER_URI = "filter_uri";

    public static final String EXTRA_FILTER_POSITION = "position";

    @BindView(R.id.dv_filter_thumb)
    SimpleDraweeView mDvImage;

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

    private int mRotation;

    private int mCurPosition;

    private PipelineDraweeController mController;

    public static void start(AppCompatActivity activity, Uri uri, int position) {
        Intent intent = new Intent(activity, FilterActivity.class);
        intent.putExtra(EXTRA_FILTER_URI, uri);
        intent.putExtra(EXTRA_FILTER_POSITION, position);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_filter);
        ButterKnife.bind(this);

        Uri uri = getIntent().getParcelableExtra(EXTRA_FILTER_URI);
        mDvImage.setImageURI(uri);
        mIvCancel.setOnClickListener(v -> finish());
        mIvrOk.setOnClickListener(v -> {
            if (mController != null) {
                EventBus.getDefault().post(new TemplateViewHolder.FilterEvent(mController, mCbApplyAll.isChecked(), mCurPosition));
            }
            finish();
        });

        mIvRotation.setOnClickListener(v -> {
            mRotation = mRotation >= 270 ? 0 : mRotation + 90;
            mDvImage.setRotation(mRotation);
        });

        mGridFilters.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mGridFilters.setHasFixedSize(true);
        mGridFilters.addItemDecoration(getDecoration());
        mGridFilters.setAdapter(createAdapter(uri));
    }

    private FilterAdapter createAdapter(Uri uri) {
        List<FilterType> dataSet = new ArrayList<>();
        dataSet.add(FilterType.Original);
        dataSet.add(FilterType.Grayscale);
        dataSet.add(FilterType.ColorFilter);
        dataSet.add(FilterType.Pixel);
        dataSet.add(FilterType.Vignette);
        dataSet.add(FilterType.Brightness);
        dataSet.add(FilterType.Sketch);
        dataSet.add(FilterType.Invert);
        dataSet.add(FilterType.Contrast);
        dataSet.add(FilterType.Sepia);
        dataSet.add(FilterType.Toon);

        FilterAdapter adapter = new FilterAdapter(this, dataSet, uri);
        adapter.setOnFilterSelectedListener(position -> {
            Postprocessor processor = adapter.getProcessor(position);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(mDvImage.getWidth(), mDvImage.getHeight()))
                    .setLocalThumbnailPreviewsEnabled(true)
                    .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                    .setProgressiveRenderingEnabled(false)
                    .setPostprocessor(processor)
                    .build();

            mController = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setTapToRetryEnabled(true)
                    .setOldController(mDvImage.getController())
                    .build();
            mDvImage.setController(mController);
        });
        return adapter;
    }

    private SpaceDecoration getDecoration() {
        SpaceDecoration spaceDecoration = new SpaceDecoration(LUtils.dp2px(10));
        spaceDecoration.setPaddingStart(false);
        spaceDecoration.setPaddingEdgeSide(false);
        return spaceDecoration;
    }

    @Override
    public void onPictureSaved(Uri uri) {
//        if (mCbApplyAll.isChecked()) {
//            EventBus.getDefault().post(new TemplateViewHolder.FilterEvent(mCurFilter));
//        } else {
//            EventBus.getDefault().post(new TemplateViewHolder.UriEvent(uri, getIntent().getIntExtra(EXTRA_FILTER_POSITION, 0)));
//        }
        finish();
    }

}
