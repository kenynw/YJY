package com.miguan.yjy.module.template;

import android.app.Activity;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplateViewHolder extends BaseViewHolder<Product> implements OnImageSelectListener {

    @BindView(R.id.dv_template_1_avatar)
    SimpleDraweeView mDvAvatar;

    @BindView(R.id.tv_template_1_username)
    TextView mTvUsername;

    @BindView(R.id.tv_template_delete)
    TextView mTvDelete;

    @BindView(R.id.fl_template_0_image)
    FrameLayout mFlImage;

    private ImageProvider mImageProvider;

    public TemplateViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_template_0);
        ButterKnife.bind(this, itemView);

        mImageProvider = new ImageProvider((Activity) getContext());
    }

    @Override
    public void setData(Product data) {
        mFlImage.setOnClickListener(v -> mImageProvider.getImageFromCameraOrAlbum(this));
    }

    @Override
    public void onImageSelect() {

    }

    @Override
    public void onImageLoaded(Uri uri) {
        FilterActivity.start(getContext(), uri);
    }

    @Override
    public void onError() {

    }

}
