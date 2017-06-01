package com.miguan.yjy.module.template;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.wasabeef.fresco.processors.filter.IF1977Filter;
import jp.wasabeef.fresco.processors.filter.IFAmaroFilter;
import jp.wasabeef.fresco.processors.filter.IFBrannanFilter;
import jp.wasabeef.fresco.processors.filter.IFHudsonFilter;
import jp.wasabeef.fresco.processors.filter.IFInkwellFilter;
import jp.wasabeef.fresco.processors.filter.IFNormalFilter;
import jp.wasabeef.fresco.processors.filter.IFRiseFilter;
import jp.wasabeef.fresco.processors.filter.IFSierraFilter;
import jp.wasabeef.fresco.processors.filter.IFSutroFilter;
import jp.wasabeef.fresco.processors.filter.IFValenciaFilter;
import jp.wasabeef.fresco.processors.filter.IFXproIIFilter;
import jp.wasabeef.fresco.processors.gpu.BrightnessFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.ContrastFilterPostprocessor;
import jp.wasabeef.fresco.processors.gpu.GPUFilterPostprocessor;
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

    private Context mContext;

    private List<FilterType> mTypeList;

    private List<GPUFilterPostprocessor> mPostprocessors;

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
        BlurAndGrayscale,
        Amaro,
        Brannan,
        Hudson,
        Inkwell,
        Rise,
        Sierra,
        Sutro,
        Valencia,
        Xproll,
        IF1997
    }

    public FilterAdapter(Context context, List<FilterType> list) {
        mContext = context;
        mTypeList = list;

        mPostprocessors = new ArrayList<>();
        for (FilterType filterType : list) {
            GPUFilterPostprocessor processor = null;

            switch (filterType) {
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
                            new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f);
                    break;
                case Original:
                    processor = createPostprocessor(new IFNormalFilter(context), "normal");
                    break;
                case Amaro:
                    processor = createPostprocessor(new IFAmaroFilter(context), "amaro");
                    break;
                case Brannan:
                    processor = createPostprocessor(new IFBrannanFilter(context), "brannan");
                    break;
                case Hudson:
                    processor = createPostprocessor(new IFHudsonFilter(context), "Hudson");
                    break;
                case Inkwell:
                    processor = createPostprocessor(new IFInkwellFilter(context), "Inkwell");
                    break;
                case Rise:
                    processor = createPostprocessor(new IFRiseFilter(context), "Rise");
                    break;
                case Sierra:
                    processor = createPostprocessor(new IFSierraFilter(context), "Sierra");
                    break;
                case Sutro:
                    processor = createPostprocessor(new IFSutroFilter(context), "Sutro");
                    break;
                case Valencia:
                    processor = createPostprocessor(new IFValenciaFilter(context), "Valencia");
                    break;
                case Xproll:
                    processor = createPostprocessor(new IFXproIIFilter(context), "Xproll");
                    break;
                case IF1997:
                    processor = createPostprocessor(new IF1977Filter(context), "IF1997");
                    break;
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
        ImageRequest request = ImageRequestBuilder.newBuilderWithResourceId(R.mipmap.def_image_filter)
                .setResizeOptions(new ResizeOptions(65, 92))
                .setLocalThumbnailPreviewsEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(false)
                .setPostprocessor(mPostprocessors.get(position))
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.mDvImage.getController())
                .build();

        holder.mDvImage.setController(controller);
        holder.mTvName.setText(mTypeList.get(position).name());
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onFilterSelected(mPostprocessors.get(position).getFilter());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    private GPUFilterPostprocessor createPostprocessor(GPUImageFilter filter, String key) {
        return new GPUFilterPostprocessor(mContext, filter) {
            @Nullable
            @Override
            public CacheKey getPostprocessorCacheKey() {
                return new SimpleCacheKey(key);
            }
        };
    }

    public void setOnFilterSelectedListener(OnFilterSelectedListener listener) {
        mListener = listener;
    }

    public interface OnFilterSelectedListener {
        void onFilterSelected(GPUImageFilter filter);
    }

    class FilterViewHolder extends BaseViewHolder<FilterType> {

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
