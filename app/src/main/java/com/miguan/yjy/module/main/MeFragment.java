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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.miguan.yjy.R;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.services.ServiceException;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.common.LargeImageActivity;
import com.miguan.yjy.module.product.QueryCodeActivity;
import com.miguan.yjy.module.test.WikiAskActivityPresenter;
import com.miguan.yjy.module.user.EvaluateListActivity;
import com.miguan.yjy.module.user.FaceScorePresenter;
import com.miguan.yjy.module.user.FeedbackActivity;
import com.miguan.yjy.module.user.MsgListActivity;
import com.miguan.yjy.module.user.ProductLikeListActivity;
import com.miguan.yjy.module.user.ReplyListActivity;
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

        mLlInfo.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginActivity.class )));
        mTvSkinTest.setOnClickListener(v -> EventBus.getDefault().post(2));
        mBtnUsed.setOnClickListener(v -> startActivity(new Intent(getActivity(), UsedListActivity.class)));
        mBtnLike.setOnClickListener(v -> startActivity(new Intent(getActivity(), ProductLikeListActivity.class)));
        mBtnComment.setOnClickListener(v -> startActivity(new Intent(getActivity(), EvaluateListActivity.class)));
        mBtnReply.setOnClickListener(v -> startActivity(new Intent(getActivity(), ReplyListActivity.class)));
        mBtnStar.setOnClickListener(v -> startActivity(new Intent(getActivity(), StarListActivity.class)));
        mBtnMessage.setOnClickListener(v -> startActivity(new Intent(getActivity(), MsgListActivity.class)));
        mBtnQueryNo.setOnClickListener(v -> startActivity(new Intent(getActivity(), QueryCodeActivity.class)));
        mBtnFeedback.setOnClickListener(v -> startActivity(new Intent(getActivity(), FeedbackActivity.class)));
        mBtnCommend.setOnClickListener(v -> showSharePopupWindow());

        mBadgeMsg = createBadge(mBtnMessage);
        mBadgeOverdue = createBadge(mBtnUsed);

        return view;
    }

    @Override
    public void setData(User user) {
        mLlInfo.setOnClickListener(v -> WikiAskActivityPresenter.start(getActivity(), "1"));
//        mLlInfo.setOnClickListener(v -> ProfilePresenter.start(getActivity(), user));
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

    @Override
    public void onError(Throwable throwable) {
        EventBus.getDefault().post(0);
        if (throwable instanceof ServiceException) {
            if (((ServiceException) throwable).getCode() == -6) {
                AccountModel.getInstance().clearToken();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        }
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

    private Badge createBadge(View view) {
        return new QBadgeView(getActivity())
                .bindTarget(view)
                .setBadgeGravity(Gravity.CENTER | Gravity.END)
                .setGravityOffset(30, 0, true);
    }

    private void showSharePopupWindow() {
        new SharePopupWindow.Builder(getActivity())
                .setViewTitle("推荐颜究院给")
                .setTitle("颜究院，一款专业的护肤应用")
                .setContent("查成分、测肤质，颜究院根据肤质推荐安全有效护肤品")
                .setUrl("http://m.yjyapp.com/site/download")
                .setWxCircleTitle("颜究院，帮你查成分、测肤质，根据肤质推荐安全有效护肤品，你还不用吗？")
                .setWbContent("颜究院，帮你查成分、测肤质，根据肤质推荐安全有效护肤品，你还不用吗？#颜究院APP# http://m.yjyapp.com/site/download")
                .show(mLlInfo);
    }

}
