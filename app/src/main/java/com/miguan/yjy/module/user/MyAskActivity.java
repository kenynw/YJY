package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miguan.yjy.module.user.MyAskPresenter.TYPE_ANSWER;
import static com.miguan.yjy.module.user.MyAskPresenter.TYPE_ASK;

/**
 * Copyright (c) 2017/8/25. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(MyAskPresenter.class)
public class MyAskActivity extends BaseListActivity<MyAskPresenter> {

    @BindView(R.id.rb_my_ask_question)
    RadioButton mRbQuestion;

    @BindView(R.id.rb_my_ask_answer)
    RadioButton mRbAnswer;

    @BindView(R.id.rg_my_ask_tab)
    RadioGroup mRgTab;

    private int mType = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_ask);
        ButterKnife.bind(this);
        mRgTab.setOnCheckedChangeListener((group, checkedId) -> {
            mType = (checkedId == R.id.rb_my_ask_answer ? TYPE_ANSWER : TYPE_ASK);
            getPresenter().setType(mType);
            getPresenter().onRefresh();
        });
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new MyAnswerViewHolder(parent, mType);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig()
                .setFooterNoMoreRes(R.layout.include_default_footer)
                .setContainerLayoutRes(R.layout.user_activity_ask);
    }

}
