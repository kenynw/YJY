package com.miguan.yjy.module.template;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.fresco.processors.BlurPostprocessor;
import jp.wasabeef.fresco.processors.ColorFilterPostprocessor;
import jp.wasabeef.fresco.processors.CombinePostProcessors;
import jp.wasabeef.fresco.processors.GrayscalePostprocessor;
import jp.wasabeef.fresco.processors.MaskPostprocessor;
import jp.wasabeef.fresco.processors.gpu.BrightnessFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.ContrastFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.InvertFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.KuawaharaFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.PixelationFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.SepiaFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.SketchFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.SwirlFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.ToonFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.VignetteFilterPostprocessor;


/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    private Uri mUri;

    private List<FilterType> mTypeList;

    private List<Postprocessor> mPostprocessors;

    private OnFilterSelectedListener mListener;

    enum FilterType {
        Original,
        Mask,
        NinePatchMask,
        ColorFilter,
        Grayscale,
        Blur,
        Toon,
        Sepia,
        Contrast,
        Invert,
        Pixel,
        Sketch,
        Swirl,
        Brightness,
        Kuawahara,
        Vignette,
        BlurAndGrayscale
    }

    public FilterAdapter(Context context, List<FilterType> list, Uri uri) {
        mTypeList = list;
        mUri = uri;

        mPostprocessors = new ArrayList<>();
        for (FilterType filterType : list) {
            Postprocessor processor = null;

            switch (filterType) {
                case Mask: {
                    processor = new MaskPostprocessor(context, R.mipmap.ic_launcher);
                    break;
                }
                case NinePatchMask: {
                    processor = new MaskPostprocessor(context, R.mipmap.ic_launcher);
                    break;
                }
                case ColorFilter:
                    processor = new ColorFilterPostprocessor(Color.argb(40, 200, 0, 0));
                    break;
                case Grayscale:
                    processor = new GrayscalePostprocessor();
                    break;
                case Blur:
                    processor = new BlurPostprocessor(context, 25);
                    break;
                case BlurAndGrayscale:
                    processor = new CombinePostProcessors.Builder()
                            .add(new BlurPostprocessor(context, 25))
                            .add(new GrayscalePostprocessor())
                            .build();
                    break;
                case Toon:
                    processor = new ToonFilterPostprocessor(context);
                    break;
                case Sepia:
                    processor = new SepiaFilterPostprocessor(context);
                    break;
                case Contrast:
                    processor = new ContrastFilterPostprocessor(context, 2.0f);
                    break;
                case Invert:
                    processor = new InvertFilterPostprocessor(context);
                    break;
                case Pixel:
                    processor = new PixelationFilterPostprocessor(context, 10f);
                    break;
                case Sketch:
                    processor = new SketchFilterPostprocessor(context);
                    break;
                case Swirl:
                    processor = new SwirlFilterPostprocessor(context, 0.5f, 1.0f, new PointF(0.5f, 0.5f));
                    break;
                case Brightness:
                    processor = new BrightnessFilterPostprocessor(context, 0.5f);
                    break;
                case Kuawahara:
                    processor = new KuawaharaFilterPostprocessor(context, 25);
                    break;
                case Vignette:
                    processor = new VignetteFilterPostprocessor(context, new PointF(0.5f, 0.5f),
                            new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f);
            }
            mPostprocessors.add(processor);
        }
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterViewHolder(parent, R.layout.item_grid_filter);
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Postprocessor processor = null;

        holder.mDvImage.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(mUri)
                .setResizeOptions(new ResizeOptions(65, 92))
                .setLocalThumbnailPreviewsEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(false)
                .setPostprocessor(mPostprocessors.get(position))
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setTapToRetryEnabled(true)
                        .setOldController(holder.mDvImage.getController())
                        .build();

        try {
            holder.mDvImage.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.mTvName.setText(mTypeList.get(position).name());
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onFilterSelected(position);
            }
        });
    }

    public Postprocessor getProcessor(int position) {
        return mPostprocessors.get(position);
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    public void setOnFilterSelectedListener(OnFilterSelectedListener listener) {
        mListener = listener;
    }

    public interface OnFilterSelectedListener {
        void onFilterSelected(int position);
    }

    class FilterViewHolder extends BaseViewHolder<Filter> {

        @BindView(R.id.dv_filter_image)
        SimpleDraweeView mDvImage;

        @BindView(R.id.tv_filter_name)
        TextView mTvName;

        public FilterViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            ButterKnife.bind(this, itemView);
        }

    }

}
