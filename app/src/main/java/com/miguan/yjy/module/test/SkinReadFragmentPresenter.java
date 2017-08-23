package com.miguan.yjy.module.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.MySkinAdapter;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.module.user.FeedbackActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @作者 cjh
 * @日期 2017/6/16 15:41
 * @描述
 */

public class SkinReadFragmentPresenter extends BaseListFragmentPresenter<SkinReadFragment, Skin> {

    public static final String H5_SCORE = "http://m.yjyapp.com/site/score-tip";

    private List<Skin> mSkinList = new ArrayList<>();
    Test mTest;

    @Override
    protected void onCreate(SkinReadFragment view, Bundle saveState) {
        super.onCreate(view, saveState);

    }

    @Override
    protected void onCreateView(SkinReadFragment view) {
        super.onCreateView(view);
//        mTest = getView().getArguments().getParcelable(SkinTestViewPager.BUNDLE_TEST);
        mTest = getView().mTest;
        getAdapter().removeAllHeader();
        getAdapter().addHeader(new RecyclerArrayAdapter.ItemView() {

            private GridView mEasyRecyclerView;

            @Override
            public View onCreateView(ViewGroup parent) {
                return View.inflate(getView().getContext(), R.layout.include_head_view_my_skin, null);
            }

            @Override
            public void onBindView(View headerView) {
                headerView.findViewById(R.id.iv_skin_rules).setOnClickListener(v -> WebViewActivity.start(getView().getActivity(), getView().getActivity().getString(R.string.text_test_grade), H5_SCORE));

                mEasyRecyclerView = headerView.findViewById(R.id.recy_my_skin);

                mSkinList = mTest.getDesc();
                mTest.getDescribe();
                MySkinAdapter mySkinAdapter = new MySkinAdapter(getView().getActivity(), mSkinList);
                mEasyRecyclerView.setAdapter(mySkinAdapter);
                mySkinAdapter.notifyDataSetChanged();
            }
        });

        getAdapter().removeAllFooter();
        getAdapter().addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View footSkin = View.inflate(getView().getContext(), R.layout.inlucde_foot_skin_view, null);
                return footSkin;
            }

            @Override
            public void onBindView(View headerView) {
                headerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getView().getActivity(), FeedbackActivity.class);
                        getView().getActivity().startActivity(intent);
                    }
                });
            }
        });

        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        Observable.just(mTest.getDesc()).unsafeSubscribe(getRefreshSubscriber());
//        TestModel.getInstantce().getSkinList().unsafeSubscribe(getRefreshSubscriber());
//        UserModel.getInstance().getUserInfo().subscribe(new ServicesResponse<User>(){
//
//            @Override
//            public void onNext(User user) {
//                super.onNext(user);
//            }
//        });
//
//        TestModel.getInstantce().userSkin().subscribe(new ServicesResponse<Test>(){
//            @Override
//            public void onNext(Test test) {
//                super.onNext(test);
//
//            }
//        });
    }
}
