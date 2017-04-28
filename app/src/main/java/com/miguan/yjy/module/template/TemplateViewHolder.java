package com.miguan.yjy.module.template;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.utils.ScreenShot;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public abstract class TemplateViewHolder extends BaseViewHolder<Product> implements OnImageSelectListener, FilterActivity.OnFilterSelectedListener {

    protected Map<Uri, SimpleDraweeView> mDraweeViewMap;

    public TemplateViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this, itemView);
        mDraweeViewMap = new HashMap<>();
    }

    protected void setImageUri(Uri uri, SimpleDraweeView draweeView) {
        draweeView.setImageURI(uri);
        mDraweeViewMap.put(uri, draweeView);
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public abstract void setFilter(Postprocessor processor);

    public Bitmap takeShot(int width) {
//        itemView.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        itemView.layout(0, 0, itemView.getMeasuredWidth(), itemView.getMeasuredHeight());

//        itemView.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        itemView.layout(0, 0, itemView.getMeasuredWidth(), itemView.getMeasuredHeight());

//        v.setDrawingCacheEnabled(true);
//        v.buildDrawingCache();
//        Bitmap bitmap = v.getDrawingCache();
//        v.setDrawingCacheEnabled(false);
//        v.destroyDrawingCache();

        return ScreenShot.getInstance().takeScreenShotOfJustView(itemView);
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

}
