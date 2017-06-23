package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.article.EvaluateReplyViewHolder;

import java.util.List;

/**
 * Copyright (c) 2017/6/16. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateDetailPresenter extends BaseDataActivityPresenter<EvaluateDetailActivity, Evaluate>
        implements RecyclerArrayAdapter.OnMoreListener {

    public static final String EXTRA_EVALUATE_ID = "evaluate_id";

    public static void start(Context context, int evaluateId) {
        Intent intent = new Intent(context, EvaluateDetailActivity.class);
        intent.putExtra(EXTRA_EVALUATE_ID, evaluateId);
        context.startActivity(intent);
    }

    private int mEvaluateId;

    private EvaluateAdapter mAdapter;

    private int mCurPage;

    @Override
    protected void onCreate(EvaluateDetailActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mAdapter = new EvaluateAdapter(getView());
        mAdapter.setMore(R.layout.default_footer_load_more, this);
        mAdapter.setNoMore(R.layout.default_footer_no_more);
        mAdapter.setOnItemClickListener(getView());

        mEvaluateId = getView().getIntent().getIntExtra(EXTRA_EVALUATE_ID, 0);
    }

    @Override
    protected void onCreateView(EvaluateDetailActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    public void onRefresh() {
        ArticleModel.getInstance().getEvaluateDetail(mEvaluateId)
                .doOnNext(evaluate -> {
                    List<Evaluate> evaluates = evaluate.getReplyList();
                    if (evaluates != null && evaluates.size() > 0) {
                        mAdapter.clear();
                        mAdapter.addAll(evaluates);
                        mCurPage = 2;
                    }
                })
                .unsafeSubscribe(getDataSubscriber());
    }

    @Override
    public void onMoreShow() {
        ArticleModel.getInstance().getReplyList(mEvaluateId, mCurPage)
                .unsafeSubscribe(new ServicesResponse<List<Evaluate>>() {
                    @Override
                    public void onNext(List<Evaluate> evaluates) {
                        mAdapter.addAll(evaluates);
                        mCurPage++;
                    }
                });
    }

    @Override
    public void onMoreClick() {
        onRefresh();
    }

    public void addReply(String content) {
        if (isLogin()) {
            ArticleModel.getInstance().addEvaluate(getData().getPost_id(), getData().getType(),
                    getView().getEvaluate() != null ? getView().getEvaluate().getId() : mEvaluateId, "", content)
                    .unsafeSubscribe(new ServicesResponse<String>() {
                        @Override
                        public void onNext(String s) {
                            getView().clearInput();
                            getView().setResult(Activity.RESULT_OK);
                            onRefresh();
                        }
                    });
        }
    }

    public boolean isLogin() {
        if (UserPreferences.getUserID() > 0) {
            return true;
        }
        getView().startActivity(new Intent(getView(), LoginActivity.class));
        return false;
    }

    public Evaluate getItem(int position) {
        return mAdapter.getItem(position);
    }

    public EvaluateAdapter getAdapter() {
        return mAdapter;
    }

    private class EvaluateAdapter extends RecyclerArrayAdapter<Evaluate> {

        public EvaluateAdapter(Context context) {
            super(context);
        }

        @Override
        public EvaluateReplyViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new EvaluateReplyViewHolder(parent);
        }

    }

}
