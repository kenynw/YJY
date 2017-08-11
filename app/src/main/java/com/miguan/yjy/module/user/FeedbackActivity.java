package com.miguan.yjy.module.user;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(FeedbackPresenter.class)
public class FeedbackActivity extends ChainBaseActivity<FeedbackPresenter>
        implements TextWatcher, View.OnClickListener, OnImageSelectListener {

    @BindView(R.id.et_input_content)
    EditText mEtContent;

    @BindView(R.id.iv_evaluate_image)
    ImageView mIvImage;

    @BindView(R.id.iv_evaluate_delete)
    ImageView mIvDelete;

    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity_add_evaluate);
        setToolbarTitle(R.string.btn_feedback);
        ButterKnife.bind(this);

        mEtContent.setHint("请输入要反馈的内容");
        mEtContent.addTextChangedListener(this);
        mIvImage.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        menu.getItem(0).setEnabled(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (TextUtils.isEmpty(mEtContent.getText())) LUtils.toast("请输入反馈内容");
        else getPresenter().submit(mEtContent.getText().toString().trim(), mUri);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        getToolbar().getMenu().getItem(0).setEnabled(!TextUtils.isEmpty(s));
    }

    @Override
    public void onClick(View view) {
        ImageProvider.getInstance(this).getImageFromCameraOrAlbum(this);
    }

    @Override
    public void onImageSelect() {

    }

    @Override
    public void onImageLoaded(Uri uri) {
        mUri = uri;

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mIvImage.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mIvImage.setImageURI(uri);
        mIvImage.setLayoutParams(lp);

        mIvDelete.setVisibility(View.VISIBLE);
        mIvDelete.setOnClickListener(v -> {
            mUri = null;
            mIvImage.setImageResource(R.mipmap.ic_add_evaluate_def_image);
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mIvImage.setLayoutParams(lp);
            mIvDelete.setVisibility(View.GONE);
        });
    }

    @Override
    public void onError() {

    }

    @Override
    public int[] getHideSoftViewIds() {
        return new int[] {R.id.et_input_content};
    }

}
