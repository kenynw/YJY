package com.miguan.yjy.module.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.module.product.QueryCodeActivity;
import com.miguan.yjy.module.user.EvaluateListActivity;
import com.miguan.yjy.module.user.FaceScorePresenter;
import com.miguan.yjy.module.user.FeedbackActivity;
import com.miguan.yjy.module.user.MsgListActivity;
import com.miguan.yjy.module.user.ProductLikeListActivity;
import com.miguan.yjy.module.user.ProfilePresenter;
import com.miguan.yjy.module.user.StarListActivity;
import com.miguan.yjy.module.user.UsedListActivity;
import com.miguan.yjy.widget.SharePopupWindow;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

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

    @BindView(R.id.dv_me_avatar)
    ImageView mDvAvatar;

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

    private Badge mBadgeMsg;

    private Badge mBadgeOverdue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_me, container, false);
        mBind = ButterKnife.bind(this, view);

        mTvSkinTest.setOnClickListener(v -> EventBus.getDefault().post(2));
        mBtnUsed.setOnClickListener(v -> startActivity(new Intent(getActivity(), UsedListActivity.class)));
        mBtnLike.setOnClickListener(v -> startActivity(new Intent(getActivity(), ProductLikeListActivity.class)));
        mBtnComment.setOnClickListener(v -> startActivity(new Intent(getActivity(), EvaluateListActivity.class)));
        mBtnStar.setOnClickListener(v -> startActivity(new Intent(getActivity(), StarListActivity.class)));
        mBtnMessage.setOnClickListener(v -> startActivity(new Intent(getActivity(), MsgListActivity.class)));
        mBtnQueryNo.setOnClickListener(v -> startActivity(new Intent(getActivity(), QueryCodeActivity.class)));
        mBtnFeedback.setOnClickListener(v -> startActivity(new Intent(getActivity(), FeedbackActivity.class)));
        mBtnCommend.setOnClickListener(v -> showSharePopupWindow());

        mBadgeMsg = createBadge(mBtnMessage);
        mBadgeOverdue = createBadge(mBtnUsed);

        return view;
    }

    private void showSharePopupWindow() {
        new SharePopupWindow.Builder(getActivity())
                .setViewTitle("推荐颜究院给")
                .setTitle("颜究院，一款专业的护肤应用")
                .setContent("查成分、测肤质，颜究院根据肤质推荐安全有效护肤品")
                .setUrl("http://m.yjyapp.com/")
                .show(mLlInfo);
    }

    @Override
    public void setData(User user) {
        mLlInfo.setOnClickListener(v -> ProfilePresenter.start(getActivity(), user));
        mDvAvatar.setImageURI(Uri.parse(user.getImg()));
        mTvUsername.setText(user.getUsername());
        mTvSkin.setText(user.getSkin_name());

        SpannableString spannableString = new SpannableString(String.format(getString(R.string.label_looking_value), user.getRank_points()));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textYellow)), 3, 3 + user.getRank_points().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvFaceScore.setText(spannableString);
        mTvFaceScore.setOnClickListener(v -> FaceScorePresenter.start(getActivity(), user.getRank_points()));

        if (user.getOverdueNum() > 0) {
            mBadgeOverdue.setBadgeText("");
        } else {
            mBadgeOverdue.hide(false);
        }

        if (user.getUnReadNUM() > 0) {
            mBadgeMsg.setBadgeText("");
        } else {
            mBadgeMsg.hide(false);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        UserPreferences.setUserID(0);
        EventBus.getDefault().post(0);
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

    private Badge createBadge(View view) {
        return new QBadgeView(getActivity())
                .bindTarget(view)
                .setBadgeGravity(Gravity.CENTER | Gravity.END)
                .setGravityOffset(30, 0, true);
    }

}
