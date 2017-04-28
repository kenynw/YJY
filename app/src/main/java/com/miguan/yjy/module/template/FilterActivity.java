package com.miguan.yjy.module.template;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(FilterActivityPresenter.class)
public class FilterActivity extends ChainBaseActivity<FilterActivityPresenter> implements FilterAdapter.OnFilterSelectedListener {

    public static final String EXTRA_FILTER_URI = "filter_uri";

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

    private static OnFilterSelectedListener sFilterSelectedListener;

    private int mRotation;

    private Uri mUri;

    private ImageRequest mRequest;

    private Postprocessor mPostprocessor;

    public static void start(AppCompatActivity activity, Uri uri, OnFilterSelectedListener listener) {
        sFilterSelectedListener = listener;
        Intent intent = new Intent(activity, FilterActivity.class);
        intent.putExtra(EXTRA_FILTER_URI, uri);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_filter);
        ButterKnife.bind(this);

        mDvImage.setImageURI(mUri = getIntent().getParcelableExtra(EXTRA_FILTER_URI));
        mIvCancel.setOnClickListener(v -> finish());
        mIvrOk.setOnClickListener(v -> {
            if (mCbApplyAll.isChecked() && mPostprocessor != null) {
                EventBus.getDefault().post(mPostprocessor);
            }
            sFilterSelectedListener.onFilterSelected(mRequest);
            finish();
        });

        mIvRotation.setOnClickListener(v -> {
            mRotation = mRotation >= 270 ? 0 : mRotation + 90;
            mDvImage.setRotation(mRotation);
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
    public void onFilterSelected(Postprocessor postprocessor) {
        mPostprocessor = postprocessor;
        mRequest = ImageRequestBuilder.newBuilderWithSource(mUri)
                .setResizeOptions(new ResizeOptions(LUtils.getScreenWidth() / 3, LUtils.getScreenHeight() / 3))
                .setLocalThumbnailPreviewsEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(false)
                .setPostprocessor(postprocessor)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(mRequest)
                .setTapToRetryEnabled(true)
                .setOldController(mDvImage.getController())
                .build();
        mDvImage.setController(controller);
    }

    public interface OnFilterSelectedListener {
        void onFilterSelected(ImageRequest request);
    }

}
