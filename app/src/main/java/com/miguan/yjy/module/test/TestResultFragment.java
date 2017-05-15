package com.miguan.yjy.module.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.TestSkinAdapter;
import com.miguan.yjy.adapter.viewholder.ArticleViewHolder;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.module.common.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @作者 cjh
 * @日期 2017/4/1 16:43
 * @描述 测试结果(我的肤质)
 */
@RequiresPresenter(TestResultPresenter.class)
public class TestResultFragment extends BaseDataFragment<TestResultPresenter, Test> {

    public static final String H5_SCORE = "http://m.yjyapp.com/site/score-tip";

    private Unbinder mBind;

    @BindView(R.id.tv_test_result_descirbe)
    TextView mTvResultDescirbe;

    @BindView(R.id.tv_test_result_descirbe_second)
    TextView mTvResultDescirbeSecond;

    @BindView(R.id.rect_test_my_skin)
    RecyclerView mRectTestMySkin;

    @BindView(R.id.ll_test_grade)
    LinearLayout mLlTestGrade;

    @BindView(R.id.ll_test_again)
    LinearLayout mLlTestAgain;

    @BindViews({R.id.tv_test_first_letter, R.id.tv_test_second_letter, R.id.tv_test_third_letter, R.id.tv_test_four_letter})
    List<TextView> mSkinLetters;

    @BindViews({R.id.tv_test_first_name, R.id.tv_test_second_name, R.id.tv_test_third_name, R.id.tv_test_four_name})
    List<TextView> mSkinNames;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.recy_test_article)
    RecyclerView mRecyArticle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.test_activity_result, container, false);
        mBind = ButterKnife.bind(this, view);

        mToolbarTitle.setText("我的肤质");
        mLlTestGrade.setOnClickListener(v -> WebViewActivity.start(getActivity(), getString(R.string.text_test_grade), H5_SCORE));
        mLlTestAgain.setOnClickListener(v -> {
            if (mMyOnTabClick != null) {
                mMyOnTabClick.tabClickStart();
            }
        });

        mRectTestMySkin.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyArticle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    public void setData(Test test) {
        setSkinResult(test);

        mRecyArticle.setAdapter(new RecyclerView.Adapter<ArticleViewHolder>() {

            @Override
            public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ArticleViewHolder(parent);
            }

            @Override
            public void onBindViewHolder(ArticleViewHolder holder, int position) {
                holder.setData(test.getSkinArticle().get(position));
            }

            @Override
            public int getItemCount() {
                return test.getSkinArticle().size();
            }
        });

        List<Skin> datas = test.getSkinProduct();
        TestSkinAdapter testSkinAdapter = new TestSkinAdapter(getActivity(), datas);
        testSkinAdapter.setOnItemClickListener(position -> TestRecomendPresenter.star(getActivity(), test.getCategoryList(), position, datas.get(position).getCategory_name()));
        mRectTestMySkin.setAdapter(testSkinAdapter);
    }

    // 设置肤质
    private void setSkinResult(Test test) {
        List<Skin> skinList = test.getDesc();
        for (int i = 0; i < skinList.size(); i++) {
            mSkinLetters.get(i).setText(skinList.get(i).getLetter());
            mSkinNames.get(i).setText(skinList.get(i).getName());
        }
        mTvResultDescirbe.setText(test.getFeatures());
        mTvResultDescirbeSecond.setText(test.getElements());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBind.unbind();
    }

    public interface MyOnTabClick {
        void tabClickStart();
    }

    public MyOnTabClick mMyOnTabClick;

    public void setMyOnTabClick(MyOnTabClick myOnTabClick) {
        mMyOnTabClick = myOnTabClick;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
