package com.miguan.yjy.module.ask;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.module.user.MyAnswerViewHolder;

/**
 * Copyright (c) 2017/9/5. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(MyAskFragmentPresenter.class)
public class MyAskFragment extends BaseListFragment<MyAskFragmentPresenter, Ask> {

    public static final int TYPE_QUESTION = 2;

    public static final int TYPE_ANSWER = 3;

    private static final String KEY_TYPE = "type";

    private int mType;

    public static MyAskFragment get(int type) {
        MyAskFragment fragment = new MyAskFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_TYPE)) {
            mType = savedInstanceState.getInt(KEY_TYPE);
        } else {
            mType = getArguments().getInt(KEY_TYPE);
        }
    }

    @Override
    public BaseViewHolder<Ask> createViewHolder(ViewGroup parent, int viewType) {
        return new MyAnswerViewHolder(parent, mType);
    }

    @Override
    public ListConfig getListConfig() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.empty_common_list, null);
        view.setBackgroundColor(getResources().getColor(R.color.bgWindow));
        TextView tv = view.findViewById(R.id.tv_empty);

        String text = mType == TYPE_QUESTION ? "还没有问过问题哦~" : "还没有回答过问题哦~";
        tv.setText(text);

        int resId = mType == TYPE_QUESTION ? R.mipmap.ic_my_question_empty : R.mipmap.ic_my_answer_empty;
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);

        return super.getListConfig()
                .setContainerEmptyView(view)
                .setFooterNoMoreRes(R.layout.include_default_footer);
    }

    public int getType() {
        return mType;
    }

}
