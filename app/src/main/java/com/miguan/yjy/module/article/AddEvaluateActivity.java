package com.miguan.yjy.module.article;

import android.content.Intent;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(AddEvaluatePresenter.class)
public class AddEvaluateActivity extends ChainBaseActivity<AddEvaluatePresenter> implements
        OnImageSelectListener, TextWatcher {

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
        setToolbarTitle(R.string.text_add_comment);
        ButterKnife.bind(this);

        mEtContent.addTextChangedListener(this);
        mIvImage.setOnClickListener(v -> ImageProvider.getInstance(this).getImageFromCameraOrAlbum(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        checkInput();
        return super.onOptionsItemSelected(item);
    }

    private void checkInput() {
        if (TextUtils.isEmpty(mEtContent.getText())) {
            return;
        }

        getPresenter().submit(mEtContent.getText().toString().trim(), mUri);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageProvider.getInstance(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        getToolbar().getMenu().getItem(0).setEnabled(mEtContent.getText().length() > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public int[] getHideSoftViewIds() {
        return new int[] {R.id.et_input_content};
    }

}
