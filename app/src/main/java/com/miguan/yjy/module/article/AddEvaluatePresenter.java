package com.miguan.yjy.module.article;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.ImageModel;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;

import java.io.File;

import rx.Observable;
import rx.functions.Func1;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class AddEvaluatePresenter extends Presenter<AddEvaluateActivity> {

    public static final int REQUEST_CODE = 0x104;

    public static final String EXTRA_ARTICLE_ID = "article_id";

    public static final String EXTRA_TYPE = "type";

    public static final String EXTRA_PARENT_ID = "parent_id";

    public static void start(Activity context, int postId, int type, int parentId) {
        if (AccountModel.getInstance().isLogin()) {
            Intent intent = new Intent(context, AddEvaluateActivity.class);
            intent.putExtra(EXTRA_ARTICLE_ID, postId);
            intent.putExtra(EXTRA_TYPE, type);
            intent.putExtra(EXTRA_PARENT_ID, parentId);
            context.startActivityForResult(intent, REQUEST_CODE);
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    private int mArticleId;

    private int mType;

    private int mParentId;

    @Override
    protected void onCreate(AddEvaluateActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mArticleId = getView().getIntent().getIntExtra(EXTRA_ARTICLE_ID, 0);
        mType = getView().getIntent().getIntExtra(EXTRA_TYPE, 0);
        mParentId = getView().getIntent().getIntExtra(EXTRA_PARENT_ID, 0);
    }

    public void submit(String content, Uri uri) {
        if (uri != null) {
            getView().getExpansionDelegate().showProgressBar("正在上传图片");
            ImageModel.getInstance().uploadImageSync("attachment", new File(uri.getPath()))
                    .flatMap(new Func1<String, Observable<Evaluate>>() {
                        @Override
                        public Observable<Evaluate> call(String s) {
                            return ArticleModel.getInstance().addEvaluate(mArticleId, mType, mParentId, s, content);
                        }
                    })
                    .subscribe(new ServicesResponse<Evaluate>() {
                        @Override
                        public void onNext(Evaluate s) {
                            getView().setResult(Activity.RESULT_OK);
                            getView().finish();
                        }
                    });

        } else {
            ArticleModel.getInstance().addEvaluate(mArticleId, mType, mParentId, "", content)
                    .subscribe(new ServicesResponse<Evaluate>() {
                        @Override
                        public void onNext(Evaluate evaluate) {
                            getView().setResult(Activity.RESULT_OK);
                            getView().finish();
                        }
                    });
        }
    }

}
