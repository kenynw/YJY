package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.adapter.viewholder.WikiTitleViewHolder;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;

/**
 * @作者 cjh
 * @日期 2017/7/25 11:50
 * @描述
 */

public class WikiDetailActivityPresenter extends BaseDataActivityPresenter<WikiDetailActivity, Wiki> {

    public static final String EXTRA_BAIKEID = "extra_baikeId";

    private int mWikiId;

    private RecyclerArrayAdapter<Wiki> mWikiAdapter = new RecyclerArrayAdapter<Wiki>(getView()) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new WikiTitleViewHolder(parent);
        }
    };

    public static void start(Context context, int baikeId) {
        Intent intent = new Intent(context, WikiDetailActivity.class);
        intent.putExtra(EXTRA_BAIKEID, baikeId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(WikiDetailActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mWikiId = getView().getIntent().getIntExtra(EXTRA_BAIKEID, 0);
    }

    @Override
    protected void onCreateView(WikiDetailActivity view) {
        super.onCreateView(view);
        loadData();
    }

    private void loadData() {
        TestModel.getInstantce().getBaikeInfo(mWikiId).unsafeSubscribe(getDataSubscriber());
    }

    public RecyclerArrayAdapter<Wiki> getWikiAdapter() {
        return mWikiAdapter;
    }

    public void refresh(int baikeId) {
        this.mWikiId = baikeId;
        loadData();
    }

    public void addBaikeLike(Wiki wiki) {
        if (AccountModel.getInstance().isLogin()) {
            TestModel.getInstantce().addBaikeLike(mWikiId).subscribe(new ServicesResponse<String>() {
                @Override
                public void onNext(String s) {
                    getView().setLike(wiki);
                }
            });
        } else {
            getView().startActivity(new Intent(getView(), LoginActivity.class));
        }
    }

}
