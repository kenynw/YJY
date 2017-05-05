package com.miguan.yjy.module.template;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public abstract class BaseTemplateViewHolder extends BaseViewHolder<Product> implements OnImageSelectListener,
        FilterActivity.OnFilterSelectedListener, View.OnClickListener {

    @BindView(R.id.fl_template_delete)
    FrameLayout mFlDelete;

    private boolean mIsInit = false;

    public BaseTemplateViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this, itemView);
    }

    protected void setImageFilter(SimpleDraweeView dv, ImageRequest request) {
        if (request != null) {
            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setTapToRetryEnabled(true)
                    .setOldController(dv.getController())
                    .build();

            dv.setController(controller);
        }
    }

    @Override
    public void setData(Product data) {
        mFlDelete.setOnClickListener(v -> {
            mIsInit = false;
            EventBus.getDefault().post(new TemplateDeleteEvent(getDataPosition(), data));
        });
        if (!mIsInit) {
            initViews(data);
            mIsInit = true;
        }
    }

    public abstract void initViews(Product product);

    public void setInit(boolean isInit) {
        mIsInit = isInit;
    }

    @Override
    public void onClick(View v) {
        ImageProvider.getInstance((Activity) getContext()).getImageFromCameraOrAlbum(new OnImageSelectListener() {
            @Override
            public void onImageSelect() {

            }

            @Override
            public void onImageLoaded(Uri uri) {
                ImageProvider.getInstance((Activity) getContext()).corpImage(uri, 640, 640, BaseTemplateViewHolder.this);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onImageSelect() {

    }

    @Override
    public void onImageLoaded(Uri uri) {

    }

    @Override
    public void onError() {

    }

    public void hideOperatingViews() {
        mFlDelete.setVisibility(View.GONE);
    }

    public static class TemplateDeleteEvent {

        private int mPosition;

        private Product mProduct;

        public TemplateDeleteEvent(int position, Product product) {
            mPosition = position;
            mProduct = product;
        }

        public int getPosition() {
            return mPosition;
        }

        public void setPosition(int position) {
            mPosition = position;
        }

        public Product getProduct() {
            return mProduct;
        }

        public void setProduct(Product product) {
            mProduct = product;
        }
    }

}
