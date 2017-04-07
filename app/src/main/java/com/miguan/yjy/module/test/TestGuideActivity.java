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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/31 15:38
 * @描述 测试引导页
 */
@RequiresPresenter(TestGuidePresenter.class)

public class TestGuideActivity extends BaseDataActivity<TestGuidePresenter, Test> {


    public static final String EXTRA_TEST = "test";
    public static final String EXTRA_TEST_TITLE = "title";

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

    public static void start(Context context, Test test) {
        Intent intent = new Intent(context, TestGuideActivity.class);
        intent.putExtra(EXTRA_TEST, test);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_guide);
        ButterKnife.bind(this);
        Test test = getIntent().getParcelableExtra(TestGuideActivity.EXTRA_TEST);
        setToolbarTitle(test.getTitle());
        initData(test);
        mTvTestMySkin.setOnClickListener(v -> showSkinPopWindow());
        mTvTestInto.setOnClickListener(v->TestResultActivity.star(TestGuideActivity.this));
    }


    private void initData(Test test) {
        mIvTestImg.setBackgroundResource(test.getImg());
        mTvTestDescribe.setText(test.getDescribe());
    }

    private void showSkinPopWindow() {
        View view = View.inflate(TestGuideActivity.this, R.layout.test_popwindow_guide, null);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        popupWindow.setBackgroundDrawable(bg);
        popupWindow.showAtLocation(getToolbar(), Gravity.BOTTOM, 0, 0);
    }

}
