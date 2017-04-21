package com.miguan.yjy.module.template;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplateViewHolder extends BaseViewHolder<Product> {

    @BindView(R.id.fl_template_0_image)
    FrameLayout mFlImage;

    @BindView(R.id.dv_template_0_image)
    SimpleDraweeView mDvImage;

    @BindView(R.id.tv_template_0_desc)
    TextView mTvDesc;

    public TemplateViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_template_0);
        ButterKnife.bind(this, itemView);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setData(Product data) {
        mFlImage.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onImageClick(getAdapterPosition());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setFilter(FilterEvent event) {
        if (event.mApplyAll || (getAdapterPosition() == event.mPosition)) {

        }
        mDvImage.setController(event.mController);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setGpuImage(UriEvent uri) {
        if (uri.position == getAdapterPosition()) {
//            mDvImage.setImage(uri.mUri);
        }
    }

    public static class FilterEvent {

        final PipelineDraweeController mController;

        final boolean mApplyAll;

        final int mPosition;

        public FilterEvent(PipelineDraweeController controller, boolean applyAll, int position) {
            mController = controller;
            mApplyAll = applyAll;
            mPosition = position;
        }
    }

    public static class UriEvent {

        final Uri mUri;

        final int position;

        UriEvent(Uri uri, int position) {
            mUri = uri;
            this.position = position;
        }

    }

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    private OnImageClickListener mListener;

    public void setOnImageClickListener(OnImageClickListener listener) {
        mListener = listener;
    }

}
