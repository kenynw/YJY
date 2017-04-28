package com.miguan.yjy.module.template;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

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

        Uri uri = getIntent().getParcelableExtra(SaveTemplatePresenter.EXTRA_IMAGE);
        mIvThumb.setImageURI(uri);

        mIvWeixin.setOnClickListener(v -> share(uri));
    }

    private void share(Uri uri) {
        UMImage umImage = new UMImage(this, "http://oss.yjyapp.com/static/h5/images/logo/share.jpg");
//        UMImage thumb = new UMImage(this, R.mipmap.ic_launcher);
//        umImage.setThumb(thumb);

        new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN)
                .withText("hello")
                .withMedia(umImage)
                .share();
    }

}
