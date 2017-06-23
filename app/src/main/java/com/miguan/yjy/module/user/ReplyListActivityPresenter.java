package com.miguan.yjy.module.user;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Message;

/**
 * Copyright (c) 2017/6/21. LiaoPeiKun Inc. All rights reserved.
 */

public class ReplyListActivityPresenter extends BaseListActivityPresenter<ReplyListActivity, Message> {

    @Override
    protected void onCreateView(ReplyListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        UserModel.getInstance().getReplyList(1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        UserModel.getInstance().getReplyList(getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

}
