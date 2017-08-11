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
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.R;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.local.TemplatePreferences;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.utils.DateUtils;
import com.miguan.yjy.utils.LUtils;

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

    private View mHeader;

    private View mFooter;

    private List<TemplateView> mViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_gen);
        setToolbarTitle(R.string.text_title_template);
        ButterKnife.bind(this);

        mToolbar.findViewById(R.id.toolbar_back).setOnClickListener(v -> onBackPressed());
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
        setHeaderCursor(false);
        for (TemplateView templateView : mViewList) {
            templateView.prepareCapture();
        }
        getPresenter().takeShot(mLlGen);
        return super.onOptionsItemSelected(item);
    }

    // 隐藏光标
    public void setHeaderCursor(boolean visible) {
        if (mHeader != null) {
            EditText et = (EditText) mHeader.findViewById(R.id.et_template_header);
            if (et != null) {
                et.setCursorVisible(visible);
            }
            EditText et2 = (EditText) mHeader.findViewById(R.id.et_template_header_2);
            if (et2 != null) {
                et2.setCursorVisible(visible);
            }
        }
    }

    // 首次进入打开引导
    public void showGuide() {
        mIvGuide.setVisibility(View.VISIBLE);
        mIvGuide.setOnClickListener(v -> {
            if (mCurGuide >= mImages.length) {
                TemplatePreferences.setFirstDetail(false);
                mIvGuide.setVisibility(View.GONE);
            } else {
                mIvGuide.setImageResource(mImages[mCurGuide]);
                mCurGuide++;
            }
        });
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

        TemplateView templateView = (TemplateView) LayoutInflater.from(this).inflate(res, null, false);
        templateView.setOnImageClickListener(v -> {
            ImageProvider.getInstance(GenTemplateActivity.this).getImageFromCameraOrAlbum(new OnImageSelectListener() {
                @Override
                public void onImageSelect() {

                }

                @Override
                public void onImageLoaded(Uri uri) {
                    ImageProvider.getInstance(GenTemplateActivity.this).corpImage(uri, 400, 400, templateView);
                }

                @Override
                public void onError() {

                }
            });
        });

        FrameLayout layout = templateView.findViewById(R.id.fl_template_delete);
        layout.setOnClickListener(v -> {
            mLlGen.removeView(templateView);
            mViewList.remove(templateView);
            if (mViewList.size() == 1) showDeleteView(false);
        });
        if (item != null) {
            templateView.setData(item);
        }

        int index = mLlGen.indexOfChild(mFooter) != -1 ? mLlGen.indexOfChild(mFooter) : mLlGen.getChildCount();
        mLlGen.addView(templateView, index);
        mViewList.add(templateView);
        if (mViewList.size() > 1) {
            showDeleteView(true);
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

            if (AccountModel.getInstance().isLogin()) {
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

    private void showDeleteView(boolean isShow) {
        if (mViewList.size() > 0) {
            for (TemplateView templateView : mViewList) {
                FrameLayout layout = templateView.findViewById(R.id.fl_template_delete);
                if (layout.getVisibility() == (isShow ? View.GONE : View.VISIBLE)) {
                    layout.setVisibility(isShow ? View.VISIBLE : View.GONE);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(GenTemplateActivity.this)
                .setMessage("确认退出模板")
                .setNegativeButton("取消", null)
                .setPositiveButton("退出", (dialog, which) -> {
                    getPresenter().saveCraft();
                    toBack();
                })
                .show();
    }

}
