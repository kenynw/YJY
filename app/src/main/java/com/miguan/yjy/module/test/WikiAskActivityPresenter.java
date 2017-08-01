package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.model.services.DefaultTransform;

import java.util.List;

import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/7/25 11:50
 * @描述
 */

public class WikiAskActivityPresenter extends BaseListActivityPresenter<WikiAskActivity, Wiki.RelationInfo> {
    public static final String EXTRA_BAIKEID = "extra_baikeId";
    private String baikeId;

    public static void start(Context context, String baikeId) {
        Intent intent = new Intent(context, WikiAskActivity.class);
        intent.putExtra(EXTRA_BAIKEID, baikeId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(WikiAskActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        baikeId = getView().getIntent().getStringExtra(EXTRA_BAIKEID);
    }

    @Override
    protected void onCreateView(WikiAskActivity view) {
        super.onCreateView(view);
        TestModel.getInstantce().getBaikeInfo(baikeId).map(new Func1<Wiki, List<Wiki.RelationInfo>>() {

            @Override
            public List<Wiki.RelationInfo> call(Wiki wiki) {
                return wiki.getRelation_info();
            }
        })  .compose(new DefaultTransform<>()).unsafeSubscribe(getRefreshSubscriber());
        getAdapter().setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getView().mTvWikiAskName.setText("问题1");
                getView().mTvWikiAskReply.setText("回答1");
            }
        });

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
    }
}
