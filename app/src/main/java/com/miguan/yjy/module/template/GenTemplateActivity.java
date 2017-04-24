package com.miguan.yjy.module.template;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(GenTemplatePresenter.class)
public class GenTemplateActivity extends ChainBaseActivity<GenTemplatePresenter> implements OnImageSelectListener {

    @BindView(R.id.fl_template_gen_add)
    FrameLayout mFlAdd;

    @BindView(R.id.rcv_template_gen_list)
    RecyclerView mRcvList;

    @BindView(R.id.tv_template_delete)
    TextView mTvDelete;

    private ImageProvider mImageProvider;

    private int mPosition;

    private List<TemplateViewHolder> mViewHolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_gen);
        setToolbarTitle(R.string.text_title_template);
        ButterKnife.bind(this);

        mImageProvider = new ImageProvider(GenTemplateActivity.this);
        mViewHolders = new ArrayList<>();

        mRcvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRcvList.setAdapter(getPresenter().getAdapter());
        mFlAdd.setOnClickListener(v -> getPresenter().getAdapter().add(new Product()));
    }
//
//    protected BaseViewHolder createViewHolder(ViewGroup parent) {
//        TemplateViewHolder templateViewHolder = new TemplateViewHolder(parent);
//        templateViewHolder.setOnImageClickListener(position -> {
//            mPosition = position;
//            mImageProvider.getImageFromCameraOrAlbum(GenTemplateActivity.this);
//        });
//        mViewHolders.add(templateViewHolder);
//
//        return templateViewHolder;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getExpansionDelegate().showProgressBar("正在生成图片");
        getPresenter().takeShot(mRcvList);
//        ScreenShot.getInstance().saveRecyclerViewScreenshot(mRcvList, uri -> {
//            getExpansionDelegate().hideProgressBar();
//            SaveTemplatePresenter.start(GenTemplateActivity.this, uri);
//        });

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageProvider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelect() {

    }

    @Override
    public void onImageLoaded(Uri uri) {
        FilterActivity.start(this, uri, mPosition);
    }

    @Override
    public void onError() {

    }

}
