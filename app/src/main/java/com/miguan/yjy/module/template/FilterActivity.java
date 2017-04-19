package com.miguan.yjy.module.template;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.jude.exgridview.ExGridView;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */

public class FilterActivity extends AppCompatActivity {

    public static final String EXTRA_FILTER_URI = "filter_uri";

    @BindView(R.id.iv_image_edit_thumb)
    ImageView mIvThumb;

    @BindView(R.id.iv_image_edit_rotation)
    ImageView mIvRotation;

    @BindView(R.id.cb_image_edit_apply_all)
    CheckBox mCbApplyAll;

    @BindView(R.id.grid_image_edit_filters)
    ExGridView mGridFilters;

    private Uri mUri;

    public static void start(Context context, Uri uri) {
        Intent intent = new Intent(context, FilterActivity.class);
        intent.putExtra(EXTRA_FILTER_URI, uri);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_image_edit);
        ButterKnife.bind(this);

        mUri = getIntent().getParcelableExtra(EXTRA_FILTER_URI);

        mGridFilters.setAdapter(new FilterAdapter(this));
    }

}
