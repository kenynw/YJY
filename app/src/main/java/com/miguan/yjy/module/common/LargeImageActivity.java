package com.miguan.yjy.module.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.github.chrisbanes.photoview.PhotoView;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/6/14 14:11
 * @描述
 */
@RequiresPresenter(LargeImagePresenter.class)
public class LargeImageActivity extends ChainBaseActivity {

    public static final String EXTRA_IMGURI = "imgUri";

    @BindView(R.id.img_common_large)
    PhotoView mImgCommonLarge;

    String imgUri;

    @BindView(R.id.pb_browse_loading)
    ProgressBar mPbLoading;

    public static void start(Context context, String imgUri) {
        Intent intent = new Intent(context, LargeImageActivity.class);
        intent.putExtra(EXTRA_IMGURI, imgUri);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_large_image);
        ButterKnife.bind(this);

        mImgCommonLarge.setOnPhotoTapListener((view1, x, y) -> finish());
        mImgCommonLarge.setOnOutsidePhotoTapListener(imageView -> finish());

        imgUri = getIntent().getStringExtra(EXTRA_IMGURI);
        Glide.with(this)
                .load(imgUri)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mPbLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mPbLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mImgCommonLarge);

        LUtils.log(imgUri);
    }

}
