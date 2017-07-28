package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.model.services.DefaultTransform;

/**
 * @作者 cjh
 * @日期 2017/7/25 11:50
 * @描述
 */

public class WikiAskActivityPresenter extends BaseListActivityPresenter<WikiAskActivity, Wiki> {

    public static void start(Context context) {
        Intent intent = new Intent(context,WikiAskActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(WikiAskActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
    }

    @Override
    protected void onCreateView(WikiAskActivity view) {
        super.onCreateView(view);
        TestModel.getInstantce().getWikiList().compose(new DefaultTransform<>()).unsafeSubscribe(getRefreshSubscriber());
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
