package com.miguan.yjy.module.user;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.module.article.EvaluateMeViewHolder;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(EvaluateListPresenter.class)
public class EvaluateListActivity extends BaseListActivity<EvaluateListPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_evaluate);
        getListView().showProgress();
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateMeViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_common_list, null);
        TextView tv = view.findViewById(R.id.tv_empty);
        tv.setText("还没有点评过任何产品和文章哦~");

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_empty_comment);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);

        SpaceDecoration spaceDecoration = new SpaceDecoration(LUtils.dp2px(10));
        spaceDecoration.setPaddingEdgeSide(false);
        spaceDecoration.setPaddingStart(false);
        return super.getListConfig()
                .setContainerEmptyView(view)
                .setItemDecoration(spaceDecoration)
                .setContainerLayoutRes(R.layout.common_activity_list);
    }

}
