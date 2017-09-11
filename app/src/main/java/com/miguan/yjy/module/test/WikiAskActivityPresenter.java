package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;

import java.util.List;

import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/7/25 11:50
 * @描述
 */

public class WikiAskActivityPresenter extends BaseListActivityPresenter<WikiAskActivity, Wiki> {

    public static final String EXTRA_BAIKEID = "extra_baikeId";

    public int baikeId;

    public String share_url = "";

    private Drawable drawable;

    private Drawable drawableNormal;

    public static void start(Context context, int baikeId) {
        Intent intent = new Intent(context, WikiAskActivity.class);
        intent.putExtra(EXTRA_BAIKEID, baikeId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(WikiAskActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        baikeId = getView().getIntent().getIntExtra(EXTRA_BAIKEID, 0);
        drawable = getView().getResources().getDrawable(R.mipmap.ic_wiki_face_selected);
        drawableNormal = getView().getResources().getDrawable(R.mipmap.ic_wiki_face_normal);
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
                    Log.e("onRefresh", "onRefresh" + wiki.getIs_like());
                    if (wiki.getIs_like().equals("0")) {
                        wiki.setIs_like("1");
                        drawableNormal.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        getView().mTvWikiLikeNum.setCompoundDrawables(drawableNormal, null, null, null);
                    } else {
                        wiki.setIs_like("0");
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        getView().mTvWikiLikeNum.setCompoundDrawables(drawable, null, null, null);
                    }
                    getView().mTvWikiLikeNum.setOnClickListener(v -> addBaikeLike(wiki));
                    if (wiki.getLike_num() <= 0) {
                        getView().mTvWikiLikeNum.setText("");
                    } else {
                        getView().mTvWikiLikeNum.setText(wiki.getLike_num() + "");
                    }
                    getView().mTvWikiAskName.setText("Q : " + wiki.getQuestion());
                    getView().mTvWikiAskReply.setText("A : " + wiki.getContent());

                    if (TextUtils.isEmpty(wiki.getPicture())) {
                        getView().mSdvWiki.setVisibility(View.GONE);
                    } else {
                        getView().mSdvWiki.setImageURI(wiki.getPicture());
                    }
                    share_url = wiki.getShare_url();

                    getAdapter().setOnItemClickListener(position -> {
                        baikeId = wiki.getRelation_info().get(position).getId();
                        onRefresh();
                    });
                    return wiki.getRelation_info();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    public void addBaikeLike(Wiki wiki) {
        if (AccountModel.getInstance().isLogin()) {
            TestModel.getInstantce().addBaikeLike(baikeId).subscribe(new ServicesResponse<String>() {
                @Override
                public void onNext(String s) {
                    super.onNext(s);
                    drawable = getView().getResources().getDrawable(R.mipmap.ic_wiki_face_selected);
                    drawableNormal = getView().getResources().getDrawable(R.mipmap.ic_wiki_face_normal);
                    if (wiki.getIs_like().equals("1")) {
                        wiki.setIs_like("0");
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        getView().mTvWikiLikeNum.setCompoundDrawables(drawable, null, null, null);
                        wiki.setLike_num(wiki.getLike_num() + 1);
                        getView().mTvWikiLikeNum.setText((wiki.getLike_num()) + "");
                    } else {
                        wiki.setIs_like("1");
                        drawableNormal.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        getView().mTvWikiLikeNum.setCompoundDrawables(drawableNormal, null, null, null);
                        if (wiki.getLike_num() > 0) {
                            wiki.setLike_num(wiki.getLike_num() - 1);
                            if (wiki.getLike_num() == 0) {
                                getView().mTvWikiLikeNum.setText("");
                            } else {
                                getView().mTvWikiLikeNum.setText(wiki.getLike_num() + "");
                            }
                        }
                    }
                }
            });
        } else {
            getView().startActivity(new Intent(getView(), LoginActivity.class));
        }

    }

}
