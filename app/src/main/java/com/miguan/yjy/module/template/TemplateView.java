package com.miguan.yjy.module.template;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.R;
import com.miguan.yjy.widget.ClearEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import jp.wasabeef.fresco.processors.gpu.GPUFilterPostprocessor;

/**
 * Copyright (c) 2017/5/19. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplateView extends LinearLayout implements OnImageSelectListener, FilterActivity.OnFilterSelectedListener {


    private FrameLayout mFlDelete;

    protected List<SimpleDraweeView> mDvImages = new ArrayList<>();

    private List<ImageView> mIvFilters = new ArrayList<>();

    private List<ClearEditText> mEditTexts = new ArrayList<>();

    private SparseArray<String> mUris = new SparseArray<>();

    private int mCurPosition;

    private OnClickListener mListener;

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
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void findAllChild(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof SimpleDraweeView) {
                SimpleDraweeView dv = (SimpleDraweeView) child;
                mDvImages.add(dv);
                dv.setOnClickListener(v -> {
//                    ImageProvider.getInstance((Activity) getContext()).getImageFromCameraOrAlbum(this);
                    if (mListener != null) mListener.onClick(v);
                    mCurPosition = mDvImages.indexOf(dv);
                });
            }

            if (child.getTag() != null && viewGroup.getChildAt(i).getTag().equals("filter")) {
                ImageView iv = (ImageView) child;
                mIvFilters.add(iv);
                iv.setOnClickListener(v -> {
                    boolean roundAsCircle = false;
                    RoundingParams roundingParams;
                    if ((roundingParams = mDvImages.get(mCurPosition).getHierarchy().getRoundingParams()) != null) {
                        roundAsCircle = roundingParams.getRoundAsCircle();
                    }

                    int index = mIvFilters.indexOf(iv);
                    FilterActivity.start((AppCompatActivity) getContext(), mUris.get(mDvImages.get(index).getId(), ""),
                            roundAsCircle, TemplateView.this);
                    mCurPosition = mIvFilters.indexOf(iv);
                });
            }

            if (child instanceof EditText) {
                mEditTexts.add((ClearEditText) child);
            }

            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                findAllChild((ViewGroup) viewGroup.getChildAt(i));
            }

        }
    }

    public void setOnImageClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public SparseArray<String> getUris() {
        return mUris;
    }

    public SparseArray<String> getTexts() {
        SparseArray<String> ets = new SparseArray<>();
        for (EditText editText : mEditTexts) {
            ets.put(editText.getId(), editText.getText().toString().trim());
        }
        return ets;
    }

    public List<ClearEditText> getEditTexts() {
        return mEditTexts;
    }

    public void setData(Template.Item item) {
        if (mDvImages.size() <= 0) {
            findAllChild(this);
        }

        if (item.getUris().size() > 0) {
            mUris = item.getUris();
            for (SimpleDraweeView dvImage : mDvImages) {
                String uri = item.getUris().get(dvImage.getId(), "");
                if (!TextUtils.isEmpty(uri)) {
                    dvImage.setImageURI(Uri.parse(uri));
                    mIvFilters.get(mDvImages.indexOf(dvImage)).setVisibility(VISIBLE);
                }
            }
        }

        if (item.getEtContents().size() > 0) {
            for (int i = 0; i < item.getEtContents().size(); i++) {
                int id = item.getEtContents().keyAt(i);
                if (id > 0) {
                    EditText editText = ButterKnife.findById(this, id);
                    if (editText != null) {
                        editText.setText(item.getEtContents().valueAt(i));
                    }
                }
            }

        }
    }

    public void setAllFilter(GPUFilterPostprocessor postprocessor) {
        for (int i=0; i<mDvImages.size(); i++) {
            SimpleDraweeView dvImage = mDvImages.get(i);
            if (!TextUtils.isEmpty(mUris.get(dvImage.getId())) && dvImage.getId() != mDvImages.get(mCurPosition).getId()) {
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(mUris.get(dvImage.getId())))
                        .setResizeOptions(new ResizeOptions(30, 30))
                        .setPostprocessor(postprocessor)
                        .build();

                PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(dvImage.getController())
                        .build();

                dvImage.setController(controller);
            }
        }
    }

    @Override
    public void onImageLoaded(Uri uri) {
        int id = mDvImages.get(mCurPosition).getId();
        if (!TextUtils.isEmpty(mUris.get(id)) && mUris.get(id).equals(uri.toString())) return;

        if (uri != null) {
            mUris.put(id, uri.toString());
            mDvImages.get(mCurPosition).setImageURI(uri);
            ImageView filter = mIvFilters.get(mCurPosition);
            if (filter.getVisibility() == GONE) filter.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFilterSelected(File file, boolean applyAll) {
        Uri uri = Uri.fromFile(file);
        mDvImages.get(mCurPosition).setImageURI(uri);
        mUris.put(mDvImages.get(mCurPosition).getId(), uri.toString());
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
        for (ClearEditText editText : mEditTexts) {
            editText.setCursorVisible(false);
        }
    }

    // 完成截图
    public void completeCapture() {
        mFlDelete.setVisibility(View.VISIBLE);
        for (int i = 0; i < mDvImages.size(); i++) {
            int id = mDvImages.get(i).getId();
            if (!TextUtils.isEmpty(mUris.get(id, ""))) {
                mIvFilters.get(i).setVisibility(VISIBLE);
            }
        }
        for (ClearEditText editText : mEditTexts) {
            editText.setCursorVisible(true);
        }
    }

}
