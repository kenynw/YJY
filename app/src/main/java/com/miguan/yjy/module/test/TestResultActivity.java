package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.TestListAdapter;
import com.miguan.yjy.adapter.viewholder.ArticleViewHolder;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.module.common.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/4/1 16:43
 * @描述 测试结果(我的肤质)
 */
@RequiresPresenter(TestResultPresenter.class)
public class TestResultActivity extends BaseListActivity<TestResultPresenter> {

    public static final String H5_SCORE="http://m.yjyapp.com/site/score-tip";

    @BindView(R.id.tv_test_result_descirbe)
    TextView mTvResultDescirbe;
    @BindView(R.id.tv_test_result_descirbe_second)
    TextView mTvResultDescirbeSecond;
    @BindView(R.id.tv_test_first)
    TextView mTvTestFirst;
    @BindView(R.id.rect_test_first)
    RecyclerView mRectTestFirst;
    @BindView(R.id.ll_test_first_more)
    LinearLayout mLlTestFirstMore;
    @BindView(R.id.tv_test_sencond)
    TextView mTvTestSencond;
    @BindView(R.id.recy_test_sencond)
    RecyclerView mRecyTestSencond;
    @BindView(R.id.ll_test_sencond_more)
    LinearLayout mLlTestSencondMore;
    @BindView(R.id.ll_test_grade)
    LinearLayout mLlTestGrade;
    @BindView(R.id.ll_test_again)
    LinearLayout mLlTestAgain;
    @BindView(R.id.recycle)
    EasyRecyclerView mRecycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("我的肤质");
        ButterKnife.bind(this);
        mLlTestGrade.setOnClickListener(v-> WebViewActivity.satr(this,getString(R.string.text_test_grade),H5_SCORE));
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup prent, int viewType) {
        return new ArticleViewHolder(prent);
    }

    @Override
    protected int getLayout() {
        return R.layout.test_activity_result;
    }

    public static void star(Context context) {
        Intent intent = new Intent(context, TestResultActivity.class);
        context.startActivity(intent);
    }

    public void setData(List<Test> datas) {
        mRectTestFirst.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRectTestFirst.setAdapter(new TestListAdapter(TestResultActivity.this, datas));
    }


}
