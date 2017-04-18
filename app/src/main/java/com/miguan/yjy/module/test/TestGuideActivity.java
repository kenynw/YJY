package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.constant.Constants;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.module.common.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/31 15:38
 * @描述 测试引导页
 */
@RequiresPresenter(TestGuidePresenter.class)

public class TestGuideActivity extends BaseDataActivity<TestGuidePresenter, Test> implements View.OnClickListener {


    public static final String EXTRA_TEST = "test";
    public static final String EXTRA_TEST_TYPE = "type";
    public static final String EXTRA_TEST_TITLE = "title";
    public static final int EXTRA_TEST__FIRST_TYPE = 1;
    public static final int EXTRA_TEST_SECOND_TYPE = 2;
    public static final int EXTRA_TEST_THIRD_TYPE = 3;
    public static final int EXTRA_TEST_FOUR_TYPE = 4;

    @BindView(R.id.tv_test_my_skin)
    TextView mTvTestMySkin;
    @BindView(R.id.tv_test_into)
    TextView mTvTestInto;
    @BindView(R.id.iv_test_img)
    ImageView mIvTestImg;
    @BindView(R.id.tv_test_describe)
    TextView mTvTestDescribe;
    @BindView(R.id.tv_test_describe_sencond)
    TextView mTvTestDescribeSencond;
    View view;
    PopupWindow popupWindow;

    public static void start(Context context, Test test, int type) {
        Intent intent = new Intent(context, TestGuideActivity.class);
        intent.putExtra(EXTRA_TEST, test);
        intent.putExtra(EXTRA_TEST_TYPE, type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_guide);
        ButterKnife.bind(this);
        Test test = getIntent().getParcelableExtra(TestGuideActivity.EXTRA_TEST);
        int type = getIntent().getIntExtra(TestGuideActivity.EXTRA_TEST_TYPE, -1);
        setToolbarTitle(test.getTitle());
        initData(test);
        mTvTestMySkin.setOnClickListener(v -> showSkinPopWindow());
        view = View.inflate(TestGuideActivity.this, R.layout.test_popwindow_guide, null);
        initView();
//        TestResultActivity.star(TestGuideActivity.this)//测试结果页
        mTvTestInto.setOnClickListener(v -> WebViewActivity.satr(TestGuideActivity.this, getString(test.getTitle()), Constants.testLink + type + "&userId=" + UserPreferences.getUserID()));
    }


    private void initData(Test test) {
        mIvTestImg.setBackgroundResource(test.getImg());
        mTvTestDescribe.setText(test.getDescribe());
    }

    private void initView() {
        TextView mTvHeightDry = ButterKnife.findById(view, R.id.tv_test_height_dry);
        TextView mTvLowDry = ButterKnife.findById(view, R.id.tv_test_low_dry);
        TextView mTvLowOily = ButterKnife.findById(view, R.id.tv_test_low_oily);
        TextView mTvHeightOily = ButterKnife.findById(view, R.id.tv_test_height_oily);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        popupWindow.setBackgroundDrawable(bg);
        mTvHeightDry.setOnClickListener(this);
        mTvLowDry.setOnClickListener(this);
        mTvLowOily.setOnClickListener(this);
        mTvHeightOily.setOnClickListener(this);
    }

    private void showSkinPopWindow() {
        popupWindow.showAtLocation(getToolbar(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_test_height_dry:
                popupWindow.dismiss();
                break;
            case R.id.tv_test_low_dry:
                popupWindow.dismiss();
                break;
            case R.id.tv_test_low_oily:
                popupWindow.dismiss();
                break;
            case R.id.tv_test_height_oily:
                popupWindow.dismiss();
                break;
        }
    }
}
