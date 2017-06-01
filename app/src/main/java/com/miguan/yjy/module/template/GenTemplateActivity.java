package com.miguan.yjy.module.template;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.utils.DateUtils;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.ScreenShot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(GenTemplatePresenter.class)
public class GenTemplateActivity extends ChainBaseActivity<GenTemplatePresenter> {

    private final int[] mImages = new int[]{
            R.mipmap.image_template_guide_0, R.mipmap.image_template_guide_1,
            R.mipmap.image_template_guide_2, R.mipmap.image_template_guide_3
    };

    @BindView(R.id.fl_template_gen_add)
    FrameLayout mFlAdd;

    @BindView(R.id.ll_template_gen)
    LinearLayout mLlGen;

    @BindView(R.id.iv_template_gen_guide)
    ImageView mIvGuide;

    private int mCurGuide = 0;

    private List<BaseTemplateViewHolder> mViewHolders;

    private View mHeader;

    private View mFooter;

    private List<TemplateView> mViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_gen);
        setToolbarTitle(R.string.text_title_template);
        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        mViewHolders = new ArrayList<>();

        // 设置首次进入导航
        if (LUtils.getPreferences().getBoolean("first_template", true)) {
            mIvGuide.setVisibility(View.VISIBLE);
            mIvGuide.setOnClickListener(v -> {
                if (mCurGuide >= mImages.length) {
                    LUtils.getPreferences().edit().putBoolean("first_template", false).apply();
                    mIvGuide.setVisibility(View.GONE);
                } else {
                    mIvGuide.setImageResource(mImages[mCurGuide]);
                    mCurGuide++;
                }
            });
        } else {
            mIvGuide.setVisibility(View.GONE);
        }
    }

    public void initTemplate(int index, Template template) {
        int layoutRes = getResources().getIdentifier("item_list_template_" + index, "layout", getPackageName());
        int headerRes = getResources().getIdentifier("header_template_" + index, "layout", getPackageName());
        int footerRes = getResources().getIdentifier("footer_template_" + index, "layout", getPackageName());
        mFlAdd.setOnClickListener(v -> addItem(layoutRes, null));

        addHeader(headerRes);
        if (template != null) {
            if (mHeader != null) {
                EditText et = (EditText) mHeader.findViewById(R.id.et_template_header);
                if (et != null) {
                    et.setText(template.getTitle());
                }
                EditText et2 = (EditText) mHeader.findViewById(R.id.et_template_header_2);
                if (et2 != null) {
                    et2.setText(template.getDesc());
                }
            }
            for (Template.Item item : template.getItems()) {
                addItem(layoutRes, item);
            }
        } else {
            addItem(layoutRes, null);
        }
        addFooter(footerRes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getExpansionDelegate().showProgressBar("正在生成图片");

        getPresenter().hideCursor();

        for (BaseTemplateViewHolder viewHolder : mViewHolders) {
            viewHolder.hideOperatingViews();
        }
        ScreenShot.getInstance().takeScreenShotOfJustView(mLlGen, (path, uri) -> {
            getExpansionDelegate().hideProgressBar();
            SaveTemplatePresenter.start(GenTemplateActivity.this, path, uri);
            finish();
        });

        return super.onOptionsItemSelected(item);
    }

    public List<BaseTemplateViewHolder> getViewHolders() {
        return mViewHolders;
    }

    private void addHeader(@LayoutRes int res) {
        if (res <= 0) return;
        if (mHeader == null) {
            mHeader = LayoutInflater.from(this).inflate(res, null);
        }
        if (mHeader != null && mLlGen.indexOfChild(mHeader) != -1) {
            return;
        }
        mLlGen.addView(mHeader, 0);
    }

    public View getHeader() {
        return mHeader;
    }

    private void addItem(@LayoutRes int res, Template.Item item) {
        if (res <= 0) return;

        if (mViewList.size() >= 5) {
            LUtils.toast("最多只能添加5个");
            return;
        }

        View view = LayoutInflater.from(this).inflate(res, null, false);
        TemplateView templateView = ButterKnife.findById(view, R.id.template_view_item);
        templateView.setOnDeleteListener(() -> {
            mLlGen.removeView(view);
            mViewList.remove(view);
        });

        int index = mLlGen.indexOfChild(mFooter) != -1 ? mLlGen.indexOfChild(mFooter) : mLlGen.getChildCount();
        mLlGen.addView(templateView, index);
        mViewList.add(templateView);
        if (item != null) {
            templateView.setData(item);
        }
    }

    public List<TemplateView> getTemplateList() {
        return mViewList;
    }

    private void addFooter(@LayoutRes int res) {
        if (res <= 0) return;
        if (mFooter == null) {
            mFooter = LayoutInflater.from(this).inflate(res, null);

            TextView tvTime = ButterKnife.findById(mFooter, R.id.tv_template_time);
            if (tvTime != null) {
                tvTime.setText(DateUtils.getCurrentFormatDate("yyyy年MM月dd日"));
            }

            if (UserPreferences.getUserID() > 0) {
                SimpleDraweeView dvAvatar = ButterKnife.findById(mFooter, R.id.dv_template_avatar);
                TextView tvUsername = ButterKnife.findById(mFooter, R.id.tv_template_username);
                if (dvAvatar != null) {
                    dvAvatar.setImageURI(Uri.parse(UserPreferences.getAvatar()));
                }
                if (tvUsername != null) {
                    tvUsername.setText(UserPreferences.getUsername());
                }
            }
        }
        if (mLlGen.indexOfChild(mFooter) != -1) {
            return;
        }
        mLlGen.addView(mFooter, mLlGen.getChildCount());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(GenTemplateActivity.this)
                .setMessage("确认退出模板")
                .setNegativeButton("取消", null)
                .setPositiveButton("退出", (dialog, which) -> {
                    finish();
                }).show();
    }
}
