package com.miguan.yjy.module.template;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017/5/19. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplateView extends LinearLayout implements OnImageSelectListener, FilterActivity.OnFilterSelectedListener {

    private FrameLayout mFlDelete;

    protected List<SimpleDraweeView> mDvImages = new ArrayList<>();

    private List<ImageView> mIvFilters = new ArrayList<>();

    private SparseArray<Uri> mUris = new SparseArray<>();

    private int mCurPosition;

    private OnDeleteListener mListener;

    public TemplateView(@NonNull Context context) {
        this(context, null);
    }

    public TemplateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemplateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);
        setId(R.id.template_view_item);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        findAllChild(this);
        mFlDelete = (FrameLayout) findViewById(R.id.fl_template_delete);
        mFlDelete.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onDelete();
            }
        });
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void findAllChild(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof SimpleDraweeView) {
                SimpleDraweeView dv = (SimpleDraweeView) viewGroup.getChildAt(i);
                dv.setOnClickListener(v -> {
                    ImageProvider.getInstance((Activity) getContext()).getImageFromCameraOrAlbum(this);
                    mCurPosition = mDvImages.indexOf(dv);
                });
                mDvImages.add(dv);
            }

            if (viewGroup.getChildAt(i).getTag() != null && viewGroup.getChildAt(i).getTag().equals("filter")) {
                ImageView iv = (ImageView) viewGroup.getChildAt(i);
                mIvFilters.add(iv);
                iv.setOnClickListener(v -> {
                    boolean roundAsCircle = mDvImages.get(mCurPosition).getHierarchy().getRoundingParams() != null;
                    FilterActivity.start((AppCompatActivity) getContext(), mUris.get(mCurPosition), roundAsCircle, TemplateView.this);
                    mCurPosition = mIvFilters.indexOf(iv);
                });
            }

            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                findAllChild((ViewGroup) viewGroup.getChildAt(i));
            }
        }
    }

    public SparseArray<Uri> getUris() {
        return mUris;
    }

    public void setImages(SparseArray<Uri> uris) {
        if (mDvImages.size() <= 0) {
            findAllChild(this);
        }

        if (uris.size() > 0) {
            for (int i = 0; i < uris.size(); i++) {
                mDvImages.get(i).setImageURI(uris.valueAt(i));
            }
        }
    }

    @Override
    public void onImageLoaded(Uri uri) {
        mUris.put(mCurPosition, uri);
        mDvImages.get(mCurPosition).setImageURI(uri);
        mIvFilters.get(mCurPosition).setVisibility(View.VISIBLE);
    }

    @Override
    public void onFilterSelected(File filter, boolean applyAll) {
        if (applyAll) {
            for (SimpleDraweeView dvImage : mDvImages) {
                dvImage.setImageURI(Uri.fromFile(filter));
            }
        } else {
            mDvImages.get(mCurPosition).setImageURI(Uri.fromFile(filter));
        }
    }

    @Override
    public void onImageSelect() {

    }

    @Override
    public void onError() {

    }

    // 截图前的操作
    public void prepareCapture() {
        mFlDelete.setVisibility(View.GONE);
        for (ImageView ivFilter : mIvFilters) {
            ivFilter.setVisibility(View.GONE);
        }
        mIvFilters.get(0).setFocusableInTouchMode(true);
        mIvFilters.get(0).setFocusable(true);
    }

    private void setImageFilter(SimpleDraweeView dv, ImageRequest request) {
        if (request != null) {
            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setTapToRetryEnabled(true)
                    .setOldController(dv.getController())
                    .build();

            dv.setController(controller);
        }
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        mListener = listener;
    }

    public interface OnDeleteListener {
        void onDelete();
    }

}
