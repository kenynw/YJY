package com.miguan.yjy.module.template;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class GenTemplatePresenter extends Presenter<GenTemplateActivity> {

    public static final String EXTRA_TEMPLATE = "template";

    public static void start(Context context, TemplateType template) {
        Intent intent = new Intent(context, GenTemplateActivity.class);
        intent.putExtra(EXTRA_TEMPLATE, template);
        context.startActivity(intent);
    }

    private TemplateType mTemplate;

    private TemplateAdapter mAdapter;

    public List<Product> mProductList;

    private View mHeader;

    private View mFooter;

    @Override
    protected void onCreate(GenTemplateActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        EventBus.getDefault().register(view);

        mTemplate = (TemplateType) getView().getIntent().getSerializableExtra(EXTRA_TEMPLATE);
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
            getView().getViewHolders().add(templateViewHolder);
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
            mAdapter.add(new Product());

            if (mHeader != null) mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
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
                    mTvTime = ButterKnife.findById(view, R.id.tv_template_time);
                    if (mTvTime != null) {
                        mTvTime.setText(DateUtils.getCurrentFormatDate("yyyy年MM月dd日"));
                    }

                    if (UserPreferences.getUserID() > 0) {
                        mDvAvatar = ButterKnife.findById(view, R.id.dv_template_avatar);
                        mTvUsername = ButterKnife.findById(view, R.id.tv_template_username);
                        if (mDvAvatar != null) {
                            mDvAvatar.setImageURI(Uri.parse(UserPreferences.getAvatar()));
                        }
                        if (mTvUsername != null) {
                            mTvUsername.setText(UserPreferences.getUsername());
                        }
                    }
                }
            });
        }
        return mAdapter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getView());
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        ImageProvider.getInstance(getView()).onActivityResult(requestCode, resultCode, data);
    }

    public class TemplateAdapter extends RecyclerArrayAdapter<Product> {

        public TemplateAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return createTemplateViewHolder(parent);
        }

        public List<Product> getData() {
            return mObjects;
        }

    }
}
