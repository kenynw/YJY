package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Wiki;

import java.util.List;

import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/7/25 11:50
 * @描述
 */

public class WikiAskActivityPresenter extends BaseListActivityPresenter<WikiAskActivity, Wiki> {
    public static final String EXTRA_BAIKEID = "extra_baikeId";
    public String baikeId;
    public String share_url = "";

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
        onRefresh();

    }

    @Override
    public void onRefresh() {
        TestModel.getInstantce().getBaikeInfo(baikeId)
                .map((Func1<Wiki, List<Wiki>>) wiki -> {
                    getView().mTvWikiAskName.setText("Q : " + wiki.getQuestion());
                    getView().mTvWikiAskReply.setText("A : " + wiki.getContent());
                    share_url = wiki.getShare_url();
                    getAdapter().setOnItemClickListener(position -> {
                        baikeId = wiki.getRelation_info().get(position).getId() + "";
                        onRefresh();
                    });
                    return wiki.getRelation_info();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

}
