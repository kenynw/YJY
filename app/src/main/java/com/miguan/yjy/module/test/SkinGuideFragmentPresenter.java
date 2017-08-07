package com.miguan.yjy.module.test;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.SkinTestViewPager;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.widget.RatingBar;

import rx.Observable;

/**
 * @作者 cjh
 * @日期 2017/6/21 14:34
 * @描述
 */

public class SkinGuideFragmentPresenter extends BaseListFragmentPresenter<SkinGuideFragment, Wiki.RelationInfo> {
    private Test mTest;
    RatingBar ratbarSkin;
    @Override
    protected void onCreate(SkinGuideFragment view, Bundle saveState) {
        super.onCreate(view, saveState);
    }

    @Override
    protected void onCreateView(SkinGuideFragment view) {
        super.onCreateView(view);
        mTest = getView().getArguments().getParcelable(SkinTestViewPager.BUNDLE_TEST);
        getAdapter().removeAllHeader();
        getAdapter().setOnItemClickListener(v -> WikiAskActivityPresenter.start(getView().getActivity(),mTest.getBaike().get(v)+""));
        getAdapter().addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = View.inflate(getView().getContext(), R.layout.include_skin_guide_head, null);
                ratbarSkin = (RatingBar) view.findViewById(R.id.ratbar_skin);
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                ratbarSkin.setStar(mTest.getStar());
                ratbarSkin.setClickable(false);
            }
        });
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        Observable.just(mTest.getBaike()).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
    }

}
