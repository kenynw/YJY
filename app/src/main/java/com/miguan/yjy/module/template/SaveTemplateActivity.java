package com.miguan.yjy.module.template;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.module.main.MainActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;


/**
 * Copyright (c) 2017/4/6. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(SaveTemplatePresenter.class)
public class SaveTemplateActivity extends ChainBaseActivity<SaveTemplatePresenter> {

    @BindView(R.id.iv_template_thumb)
    ImageView mIvThumb;

    @BindViews({R.id.iv_template_weixin, R.id.iv_template_circle, R.id.iv_template_weibo})
    List<ImageView> mIvShares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_save);
        setToolbarTitle("已保存");
        ButterKnife.bind(this);

        Uri uri = getIntent().getParcelableExtra(SaveTemplatePresenter.EXTRA_IMAGE_URI);
        String path = getIntent().getStringExtra(SaveTemplatePresenter.EXTRA_IMAGE_PATH);

        mIvThumb.setImageURI(uri);

        ButterKnife.apply(mIvShares, new ButterKnife.Action<ImageView>() {
            @Override
            public void apply(@NonNull ImageView view, int index) {
                view.setOnClickListener(v -> {
                    UMImage image = new UMImage(SaveTemplateActivity.this, new File(path));
                    image.compressStyle = UMImage.CompressStyle.QUALITY;

                    ShareAction action = new ShareAction(SaveTemplateActivity.this)
                            .withMedia(image);
                    switch (index) {
                        case 0:
                            action.setPlatform(SHARE_MEDIA.WEIXIN);
                            break;
                        case 1:
                            action.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                            break;
                        case 2:
                            action.setPlatform(SHARE_MEDIA.SINA);
                            break;
                    }
                    action.share();
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
