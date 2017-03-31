package com.miguan.yjy.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Test;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/30 16:00
 * @描述 肤质测试
 */
@RequiresPresenter(TestFragmentPrensenter.class)
public class TestFragment extends BaseDataFragment<TestFragmentPrensenter, Test> {

    @BindView(R.id.iv_test_wrinkle)
    ImageView mIvTestWrinkle;
    @BindView(R.id.tv_test_wrinkle)
    TextView mTvTestWrinkle;
    @BindView(R.id.ll_test_wrinkle)
    LinearLayout mLlTestWrinkle;
    @BindView(R.id.iv_test_oily)
    ImageView mIvTestOily;
    @BindView(R.id.tv_test_oily)
    TextView mTvTestOily;
    @BindView(R.id.ll_test_oily)
    LinearLayout mLlTestOily;
    @BindView(R.id.iv_test_sensitive)
    ImageView mIvTestSensitive;
    @BindView(R.id.tv_test_sensitive)
    TextView mTvTestSensitive;
    @BindView(R.id.ll_test_sensitive)
    LinearLayout mLlTestSensitive;
    @BindView(R.id.iv_test_pigment)
    ImageView mIvTestPigment;
    @BindView(R.id.tv_test_pigment)
    TextView mTvTestPigment;
    @BindView(R.id.ll_test_pigment)
    LinearLayout mLlTestPigment;
    @BindView(R.id.tv_test_result)
    TextView mTvTestResult;
    @BindView(R.id.scr_main_test)
    ScrollView mScrMainTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.main_fragment_test, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
