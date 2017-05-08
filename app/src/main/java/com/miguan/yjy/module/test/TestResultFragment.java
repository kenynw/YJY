package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;
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
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.TestSkinAdapter;
import com.miguan.yjy.adapter.viewholder.ArticleViewHolder;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.widget.CustomNestedScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/4/1 16:43
 * @描述 测试结果(我的肤质)
 */
@RequiresPresenter(TestResultPresenter.class)
public class TestResultFragment extends BaseListFragment<TestResultPresenter, Article> {

    public static final String H5_SCORE = "http://m.yjyapp.com/site/score-tip";

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
    @BindView(R.id.recycle)
    EasyRecyclerView mRecycle;
    @BindView(R.id.tv_test_first_letter)
    TextView mTvFirstLetter;
    @BindView(R.id.tv_test_first_name)
    TextView mTvFirstName;
    @BindView(R.id.tv_test_second_letter)
    TextView mTvSecondLetter;
    @BindView(R.id.tv_test_second_name)
    TextView mTvSecondName;
    @BindView(R.id.tv_test_third_letter)
    TextView mTvThirdLetter;
    @BindView(R.id.tv_test_third_name)
    TextView mTvThirdName;
    @BindView(R.id.tv_test_four_letter)
    TextView mTvFourLetter;
    @BindView(R.id.tv_test_four_name)
    TextView mTvFourName;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.custom_scv)
    CustomNestedScrollView mCustomScv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        mToolbarTitle.setText("我的肤质");
        mLlTestGrade.setOnClickListener(v -> WebViewActivity.start(getActivity(), getString(R.string.text_test_grade), H5_SCORE));
        mLlTestAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyOnTabClick != null) {
                    mMyOnTabClick.tabClickStart();
                }
            }
        });
        return view;
    }

    @Override
    public BaseViewHolder createViewHolder(ViewGroup prent, int viewType) {
        return new ArticleViewHolder(prent);
    }

    @Override
    public int getLayout() {
        return R.layout.test_activity_result;
    }


    public static void star(Context context) {
        Intent intent = new Intent(context, TestResultFragment.class);
        context.startActivity(intent);
    }

    public void setData(List<Skin> datas,ArrayList<Skin> categoryList) {

        mRectTestMySkin.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        TestSkinAdapter testSkinAdapter=new TestSkinAdapter(getActivity(), datas);
        testSkinAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TestRecomendPresenter.star(getActivity(), categoryList,position);
            }
        });
        mRectTestMySkin.setAdapter(testSkinAdapter);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setLoadMoreAble(false).hasItemDecoration(false);
    }


    public interface MyOnTabClick {
        void tabClickStart();
    }

    public  MyOnTabClick mMyOnTabClick;

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
