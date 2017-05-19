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
                dv.setOnClickListener(v -> ImageProvider.getInstance((Activity) getContext())
                        .getImageFromCameraOrAlbum(new OnImageSelectListener() {
                            @Override
                            public void onImageSelect() {

                            }

                            @Override
                            public void onImageLoaded(Uri uri) {
                                ImageProvider.getInstance((Activity) getContext()).corpImage(uri, 640, 640, TemplateView.this);
                            }

                            @Override
                            public void onError() {

                            }
                        }));
                mDvImages.add(dv);
                mCurPosition = mDvImages.indexOf(dv);
            }

            if (viewGroup.getChildAt(i).getTag() != null && viewGroup.getChildAt(i).getTag().equals("filter")) {
                ImageView iv = (ImageView) viewGroup.getChildAt(i);
                mIvFilters.add(iv);
                iv.setOnClickListener(v -> {
                    FilterActivity.start((AppCompatActivity) getContext(), mUris.get(mCurPosition), TemplateView.this);
                    mCurPosition = mIvFilters.indexOf(iv);
                });
            }

            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                findAllChild((ViewGroup) viewGroup.getChildAt(i));
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
