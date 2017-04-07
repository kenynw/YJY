package com.miguan.yjy.module.template;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright (c) 2017/4/6. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(SaveTemplatePresenter.class)
public class SaveTemplateActivity extends ChainBaseActivity<SaveTemplatePresenter> {

    @BindView(R.id.iv_template_thumb)
    ImageView mIvThumb;

    @BindView(R.id.iv_template_weixin)
    ImageView mIvWeixin;

    @BindView(R.id.iv_template_circle)
    ImageView mIvCircle;

    @BindView(R.id.iv_template_weibo)
    ImageView mIvWeibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_save);
        setToolbarTitle("已保存");
        ButterKnife.bind(this);

        String uri = getIntent().getStringExtra(SaveTemplatePresenter.EXTRA_IMAGE);
        mIvThumb.setImageURI(Uri.parse(uri));
    }

}
