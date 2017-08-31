package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.model.services.ServicesResponse;

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
    Drawable drawable;
    Drawable drawableNormal;

    public static void start(Context context, String baikeId) {
        Intent intent = new Intent(context, WikiAskActivity.class);
        intent.putExtra(EXTRA_BAIKEID, baikeId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(WikiAskActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        baikeId = getView().getIntent().getStringExtra(EXTRA_BAIKEID);
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
                    share_url = wiki.getShare_url();

                    getAdapter().setOnItemClickListener(position -> {
                        baikeId = wiki.getRelation_info().get(position).getId() + "";
                        onRefresh();
                    });
                    return wiki.getRelation_info();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

//
//    action(string) － 固定值addBaikeLike
//    baikeId(int) － 评论ID
//    token(string) － token


    public void addBaikeLike(Wiki wiki) {

        TestModel.getInstantce().addBaikeLike(baikeId).subscribe(new ServicesResponse<String>() {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                drawable = getView().getResources().getDrawable(R.mipmap.ic_wiki_face_selected);
                drawableNormal = getView().getResources().getDrawable(R.mipmap.ic_wiki_face_normal);
                if (wiki.getIs_like().equals("0")) {
                    wiki.setIs_like("1");
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    getView().mTvWikiLikeNum.setCompoundDrawables(drawable, null, null, null);
                    getView().mTvWikiLikeNum.setText((wiki.getLike_num()+1)+"");
                } else {
                    wiki.setIs_like("0");
                    drawableNormal.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    getView().mTvWikiLikeNum.setCompoundDrawables(drawableNormal, null, null, null);
                    if (wiki.getLike_num() > 0) {
                        getView().mTvWikiLikeNum.setText((wiki.getLike_num()-1)+"");
                    }
                }
            }
        });
    }


}
