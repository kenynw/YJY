package com.miguan.yjy.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.module.user.StarListActivity;
import com.miguan.yjy.module.user.UsedListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(MainMePresenter.class)
public class MeFragment extends BaseDataFragment<MainMePresenter, User> {

    @BindView(R.id.iv_me_avatar)
    ImageView mIvAvatar;

    @BindView(R.id.tv_me_username)
    TextView mTvUsername;

    @BindView(R.id.tv_me_skin)
    TextView mTvSkin;

    @BindView(R.id.btn_me_used)
    Button mBtnUsed;

    @BindView(R.id.btn_me_like)
    Button mBtnLike;

    @BindView(R.id.btn_me_comment)
    Button mBtnComment;

    @BindView(R.id.btn_me_star)
    Button mBtnStar;

    @BindView(R.id.btn_me_message)
    Button mBtnMessage;

    @BindView(R.id.btn_me_query_no)
    Button mBtnQueryNo;

    @BindView(R.id.btn_me_feedback)
    Button mBtnFeedback;

    @BindView(R.id.btn_me_commend)
    Button mBtnCommend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_me, container, false);
        ButterKnife.bind(this, view);

        mBtnUsed.setOnClickListener(v -> startActivity(new Intent(getActivity(), UsedListActivity.class)));
        mBtnStar.setOnClickListener(v -> startActivity(new Intent(getActivity(), StarListActivity.class)));

        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
