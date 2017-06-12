package com.miguan.yjy.module.template;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;

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
import jp.wasabeef.fresco.processors.filter.IFValenciaFilter;
import jp.wasabeef.fresco.processors.filter.IFXproIIFilter;

/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    private final int[] RES = new int[] {
            R.mipmap.image_filter_normal, R.mipmap.image_filter_amaro,
            R.mipmap.image_filter_brannan, R.mipmap.image_filter_hudson,
            R.mipmap.image_filter_inkwell, R.mipmap.image_filter_rise,
            R.mipmap.image_filter_sierra, R.mipmap.image_filter_valencia,
            R.mipmap.image_filter_xproll
    };

    private Context mContext;

    private SparseArray<GPUImageFilter> mFilters;

    private OnItemClickListener mListener;

    public FilterAdapter(Context context) {
        mContext = context;
        mFilters = new SparseArray<>();
    }

    public void setNull() {
        for (int i = 0; i<mFilters.size(); i++) {
            GPUImageFilter filter = mFilters.get(i);
            if (filter != null) {
                filter.destroy();
            }
        }
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterViewHolder(parent, R.layout.item_grid_filter);
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        holder.mIvImage.setImageResource(RES[position]);
        holder.itemView.setOnClickListener(v -> {
//            GPUImageFilter filter = null;
            if (mListener != null) {
                try {
//                    filter = createFilter(position);
                    mListener.onItemClick(position);
                } catch (OutOfMemoryError error) {
                    setNull();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return RES.length;
    }

    private GPUImageFilter createFilter(int type) {
        if (mFilters.size() > 0 && mFilters.size() > type && mFilters.get(type) != null)
            return mFilters.get(type);
        GPUImageFilter filter;
        switch (type) {
            case 1:
                filter = new IFAmaroFilter(mContext);
                break;
            case 2:
                filter = new IFBrannanFilter(mContext);
                break;
            case 3:
                filter = new IFHudsonFilter(mContext);
                break;
            case 4:
                filter = new IFInkwellFilter(mContext);
                break;
            case 5:
                filter = new IFRiseFilter(mContext);
                break;
            case 6:
                filter = new IFSierraFilter(mContext);
                break;
//            case 7:
//                filter = new IFSutroFilter(mContext);
//                break;
            case 7:
                filter = new IFValenciaFilter(mContext);
                break;
            case 8:
                filter = new IFXproIIFilter(mContext);
                break;
            case 9:
                filter = new IF1977Filter(mContext);
                break;
            default:
                filter = new IFNormalFilter(mContext);
                break;
        }
        mFilters.put(type, filter);
        return filter;
    }

    public void setOnFilterSelectedListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class FilterViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_filter_name)
        TextView mTvName;

        @BindView(R.id.iv_filter_image)
        ImageView mIvImage;

        public FilterViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
            ButterKnife.bind(this, itemView);
        }

    }

}
