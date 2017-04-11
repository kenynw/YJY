package com.miguan.yjy.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.module.user.EvaluateListActivity;
import com.miguan.yjy.module.user.FaceScoreActivity;
import com.miguan.yjy.module.user.FeedbackActivity;
import com.miguan.yjy.module.user.MsgListActivity;
import com.miguan.yjy.module.user.ProductLikeListActivity;
import com.miguan.yjy.module.user.ProfilePresenter;
import com.miguan.yjy.module.user.StarListActivity;
import com.miguan.yjy.module.user.UsedListActivity;
import com.miguan.yjy.utils.LUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(MainMePresenter.class)
public class MeFragment extends BaseDataFragment<MainMePresenter, User> {

    @BindView(R.id.ll_me_info)
    LinearLayout mLlInfo;

    @BindView(R.id.tv_me_skin_test)
    TextView mTvSkinTest;

    @BindView(R.id.tv_me_face_score)
    TextView mTvFaceScore;

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

    private Unbinder mBind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_me, container, false);
        mBind = ButterKnife.bind(this, view);

        mTvSkinTest.setOnClickListener(v -> EventBus.getDefault().post(2));
        mTvFaceScore.setOnClickListener(v -> startActivity(new Intent(getActivity(), FaceScoreActivity.class)));
        mBtnUsed.setOnClickListener(v -> startActivity(new Intent(getActivity(), UsedListActivity.class)));
        mBtnLike.setOnClickListener(v -> startActivity(new Intent(getActivity(), ProductLikeListActivity.class)));
        mBtnComment.setOnClickListener(v -> startActivity(new Intent(getActivity(), EvaluateListActivity.class)));
        mBtnStar.setOnClickListener(v -> startActivity(new Intent(getActivity(), StarListActivity.class)));
        mBtnMessage.setOnClickListener(v -> startActivity(new Intent(getActivity(), MsgListActivity.class)));
        mBtnFeedback.setOnClickListener(v -> startActivity(new Intent(getActivity(), FeedbackActivity.class)));

        return view;
    }

    @Override
    public void setData(User user) {
        mLlInfo.setOnClickListener(v -> ProfilePresenter.start(getActivity(), user));
        LUtils.log(user.getImg());
        Glide.with(this).load(user.getImg())
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .placeholder(R.mipmap.def_image_loading)
                .error(R.mipmap.def_image_loading)
                .into(mIvAvatar);
        mTvUsername.setText(user.getUsername());
        mTvSkin.setText(user.getSkin_name());

        SpannableString spannableString = new SpannableString(String.format(getString(R.string.label_looking_value), user.getRank_points()));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textYellow)), 3, 3 + user.getRank_points().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvFaceScore.setText(spannableString);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBind.unbind();
    }
}
