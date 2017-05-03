package com.miguan.yjy.module.template;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.Presenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.library.imageprovider.ImageProvider;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.utils.DateUtils;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.ScreenShot;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class GenTemplatePresenter extends Presenter<GenTemplateActivity> {

    public static final String EXTRA_TEMPLATE = "template";

    public static void start(Context context, Template template) {
        Intent intent = new Intent(context, GenTemplateActivity.class);
        intent.putExtra(EXTRA_TEMPLATE, template);
        context.startActivity(intent);
    }

    private Template mTemplate;

    private TemplateAdapter mAdapter;

    private View mHeader;

    private View mFooter;

    private List<BaseTemplateViewHolder> mViewHolders;

    @Override
    protected void onCreate(GenTemplateActivity view, Bundle saveState) {
        super.onCreate(view, saveState);

        mViewHolders = new ArrayList<>();

        mTemplate = (Template) getView().getIntent().getSerializableExtra(EXTRA_TEMPLATE);
        if (mTemplate.mHeaderRes > 0)
            mHeader = LayoutInflater.from(getView()).inflate(mTemplate.mHeaderRes, null);
        if (mTemplate.mFooterRes > 0)
            mFooter = LayoutInflater.from(getView()).inflate(mTemplate.mFooterRes, null);
    }

    public BaseViewHolder createTemplateViewHolder(ViewGroup parent) {
        BaseTemplateViewHolder templateViewHolder = null;
        try {
            Constructor<? extends BaseTemplateViewHolder> constructor = mTemplate.mClass.getDeclaredConstructor(ViewGroup.class);
            templateViewHolder = constructor.newInstance(parent);
//            templateViewHolder.setImageProvider(mImageProvider, getView());
            mViewHolders.add(templateViewHolder);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return templateViewHolder;
    }

    public TemplateAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new TemplateAdapter(getView());
            mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                @Override
                public View onCreateView(ViewGroup parent) {
                    return mHeader;
                }

                @Override
                public void onBindView(View headerView) {

                }
            });
            if (mFooter != null) mAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {

                private SimpleDraweeView mDvAvatar;

                private TextView mTvUsername;

                private TextView mTvTime;

                @Override
                public View onCreateView(ViewGroup parent) {
                    return mFooter;
                }

                @Override
                public void onBindView(View view) {
                    mDvAvatar = ButterKnife.findById(view, R.id.dv_template_avatar);
                    mTvUsername = ButterKnife.findById(view, R.id.tv_template_username);
                    mTvTime = ButterKnife.findById(view, R.id.tv_template_time);

                    if (mDvAvatar != null)
                        mDvAvatar.setImageURI(Uri.parse(UserPreferences.getAvatar()));
                    if (mTvUsername != null) mTvUsername.setText(UserPreferences.getUsername());
                    if (mTvTime != null)
                        mTvTime.setText(DateUtils.getCurrentFormatDate("yyyy年MM月dd日"));
                }
            });
            mAdapter.add(new Product());
        }
        return mAdapter;
    }

    public void takeShot(RecyclerView recyclerView) {
        int bitmapHeight = 0;
        // 测量Header
        if (mHeader != null) {
            measureView(mHeader, recyclerView.getWidth());
            bitmapHeight = bitmapHeight + mHeader.getHeight();
        }

        // 测量Item
        measureView(mViewHolders.get(0).itemView, recyclerView.getMeasuredWidth());
        bitmapHeight = bitmapHeight + mViewHolders.get(0).itemView.getMeasuredHeight() * mAdapter.getCount();

        // 测量footer
        if (mFooter != null) {
            measureView(mFooter, recyclerView.getWidth());
            bitmapHeight = bitmapHeight + mFooter.getHeight();
        }

        Bitmap bigBitmap = Bitmap.createBitmap(recyclerView.getMeasuredWidth(), bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas bigCanvas = new Canvas(bigBitmap);
        bigCanvas.drawColor(Color.WHITE);
        Paint paint = new Paint();

        int iHeight = 0;
        if (mHeader != null) {
            mHeader.setDrawingCacheEnabled(true);
            mHeader.buildDrawingCache();
            bigCanvas.drawBitmap(mHeader.getDrawingCache(), 0f, iHeight, paint);
            mHeader.setDrawingCacheEnabled(false);
            mHeader.destroyDrawingCache();
            iHeight += mHeader.getMeasuredHeight();
        }

        for (int i = 0; i < mViewHolders.size(); i++) {
//            View view = recyclerView.getChildAt(i);
//            view.setDrawingCacheEnabled(true);
//            view.buildDrawingCache();
//            bigCanvas.drawBitmap(view.getDrawingCache(), 0f, iHeight, paint);
//            view.setDrawingCacheEnabled(false);
//            view.destroyDrawingCache();

            BaseTemplateViewHolder holder = mViewHolders.get(i);
//            mAdapter.onBindViewHolder(holder, holder.getAdapterPosition());
//            holder.itemView.setDrawingCacheEnabled(true);
//            holder.itemView.buildDrawingCache();
//            bigCanvas.drawBitmap(holder.takeShot(recyclerView.getMeasuredWidth()), 0f, iHeight, paint);
//            holder.itemView.setDrawingCacheEnabled(false);
//            holder.itemView.destroyDrawingCache();
            iHeight += holder.itemView.getMeasuredHeight();
        }

        if (mFooter != null) {
            bigCanvas.drawBitmap(ScreenShot.getInstance().takeScreenShotOfView(mFooter), 0f, iHeight, paint);
        }

        ScreenShot.getInstance().saveScreenshotToPicturesFolder(getView(), bigBitmap, (path, uri) -> {
            getView().getExpansionDelegate().hideProgressBar();
            SaveTemplatePresenter.start(getView(), path, uri);
        });
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        ImageProvider.getInstance(getView()).onActivityResult(requestCode, resultCode, data);
        LUtils.log("requestCode: " + requestCode);
    }

    public void measureView(View view, int width) {
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public class TemplateAdapter extends RecyclerArrayAdapter<Product> {

        public TemplateAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return createTemplateViewHolder(parent);
        }

    }
}
