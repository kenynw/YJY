package com.miguan.yjy.module.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.common.LargeImageActivity;
import com.miguan.yjy.module.product.QueryCodeActivity;
import com.miguan.yjy.module.user.AboutActivity;
import com.miguan.yjy.module.user.EvaluateListActivity;
import com.miguan.yjy.module.user.FaceScorePresenter;
import com.miguan.yjy.module.user.FeedbackActivity;
import com.miguan.yjy.module.user.MsgListActivity;
import com.miguan.yjy.module.user.ProductLikeListActivity;
import com.miguan.yjy.module.user.ProfilePresenter;
import com.miguan.yjy.module.user.ReplyListActivity;
import com.miguan.yjy.module.user.StarListActivity;
import com.miguan.yjy.module.user.UsedListActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static android.view.View.GONE;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(MeFragmentPresenter.class)
public class MeFragment extends BaseDataFragment<MeFragmentPresenter, User> {

    @BindView(R.id.ll_me_info)
    LinearLayout mLlInfo;

    @BindView(R.id.ll_me_test)
    LinearLayout mLlMeTest;

    @BindView(R.id.tv_me_skin_test)
    TextView mTvSkinTest;

    @BindView(R.id.tv_me_face_score)
    TextView mTvFaceScore;

    @BindView(R.id.dv_me_avatar)
    SimpleDraweeView mDvAvatar;

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

    @BindView(R.id.btn_me_reply)
    Button mBtnReply;

    @BindView(R.id.btn_me_star)
    Button mBtnStar;

    @BindView(R.id.btn_me_message)
    Button mBtnMessage;

    @BindView(R.id.btn_me_query_no)
    Button mBtnQueryNo;

    @BindView(R.id.btn_me_feedback)
    Button mBtnFeedback;

    @BindView(R.id.btn_me_about)
    Button mBtnAbout;

    private Unbinder mBind;

    private Badge mBadgeMsg;

    private Badge mBadgeOverdue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_me, container, false);
        mBind = ButterKnife.bind(this, view);

        setUnloginView();

        mTvSkinTest.setOnClickListener(v -> EventBus.getDefault().post(2));
        mBtnUsed.setOnClickListener(v -> getPresenter().toActivity(UsedListActivity.class));
        mBtnStar.setOnClickListener(v -> getPresenter().toActivity(StarListActivity.class));
        mBtnLike.setOnClickListener(v -> getPresenter().toActivity(ProductLikeListActivity.class));
        mBtnComment.setOnClickListener(v -> getPresenter().toActivity(EvaluateListActivity.class));
        mBtnReply.setOnClickListener(v -> getPresenter().toActivity(ReplyListActivity.class));
        mBtnMessage.setOnClickListener(v -> getPresenter().toActivity(MsgListActivity.class));
        mBtnQueryNo.setOnClickListener(v -> startActivity(new Intent(getActivity(), QueryCodeActivity.class)));
        mBtnFeedback.setOnClickListener(v -> getPresenter().toActivity(FeedbackActivity.class));
        mBtnAbout.setOnClickListener(v -> startActivity(new Intent(getActivity(), AboutActivity.class)));

        mBadgeMsg = createBadge(mBtnMessage, Gravity.CENTER);
        mBadgeOverdue = createBadge(mBtnUsed, Gravity.TOP);

        return view;
    }

    @Override
    public void setData(User user) {
        mLlInfo.setOnClickListener(v -> ProfilePresenter.start(getActivity(), user));
        mDvAvatar.setOnClickListener(v -> LargeImageActivity.start(getActivity(), user.getImg()));
        mDvAvatar.setImageURI(Uri.parse(user.getImg()));
        mTvUsername.setText(user.getUsername());
        mTvUsername.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_large_material));
        mTvSkin.setVisibility(View.VISIBLE);
        mTvSkin.setText(TextUtils.isEmpty(user.getSkin_name()) ? "未完成肤质测试~" : user.getSkin_name());

        mLlMeTest.setVisibility(View.VISIBLE);
        String scoreStr = String.format(getString(R.string.label_looking_value), user.getRank_points());
        mTvFaceScore.setText(Html.fromHtml(scoreStr));
        mTvFaceScore.setOnClickListener(v -> FaceScorePresenter.start(getActivity(), user.getRank_points()));

        if (user.getOverdueNum() > 0) {
            mBadgeOverdue.setBadgeNumber(user.getOverdueNum());
        } else {
            mBadgeOverdue.hide(false);
        }

        if (user.getUnReadNUM() > 0) {
            mBadgeMsg.setBadgeNumber(user.getUnReadNUM());
        } else {
            mBadgeMsg.hide(false);
        }
    }

    public void setUnloginView() {
        mDvAvatar.setImageURI("");
        mTvUsername.setText(R.string.text_login_or_register);
        mTvUsername.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_subhead_material));
        mLlInfo.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginActivity.class )));
        mTvSkin.setVisibility(GONE);
        mLlMeTest.setVisibility(GONE);
    }

    @Override
    public void onError(Throwable throwable) {
        setUnloginView();
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
            if (menuVisible) {
                getPresenter().loadData();
                getView().setVisibility(View.VISIBLE);
            } else {
                getView().setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBind.unbind();
    }

    private Badge createBadge(View view, int gravity) {
        return new QBadgeView(getActivity())
                .bindTarget(view)
                .setShowShadow(false)
                .setBadgeGravity(Gravity.END | gravity)
                .setGravityOffset(40, 10, true);
    }

}
