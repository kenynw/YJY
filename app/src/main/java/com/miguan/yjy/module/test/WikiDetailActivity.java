package com.miguan.yjy.module.test;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.module.user.FeedbackActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.ShareBottomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/7/25 11:49
 * @描述
 */
@RequiresPresenter(WikiDetailActivityPresenter.class)
public class WikiDetailActivity extends BaseDataActivity<WikiDetailActivityPresenter, Wiki> {

    @BindView(R.id.tv_wiki_ask_name)
    TextView mTvWikiAskName;

    @BindView(R.id.tv_wiki_ask_reply)
    TextView mTvWikiAskReply;

    @BindView(R.id.tv_wiki_ask_remark)
    TextView mTvWikiAskRemark;

    @BindView(R.id.dv_wiki_detail_thumb)
    SimpleDraweeView mSdvWiki;

    @BindView(R.id.tv_wiki_like_num)
    TextView mTvWikiLikeNum;

    @BindView(R.id.rv_wiki_detail_relation)
    RecyclerView mRvRelation;

    @BindView(R.id.tv_wiki_detail_close)
    TextView mTvClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wiki_activity_detail);
        setToolbarTitle("帮助");
        ButterKnife.bind(this);

        DividerDecoration decoration = new DividerDecoration(
                getResources().getColor(R.color.fed),
                1,
                LUtils.dp2px(28),
                LUtils.dp2px(28)
        );
        decoration.setDrawLastItem(false);
        mRvRelation.addItemDecoration(decoration);
        mRvRelation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvRelation.setAdapter(getPresenter().getWikiAdapter());
        mTvWikiAskRemark.setOnClickListener(v -> startActivity(new Intent(this, FeedbackActivity.class)));
        mTvClose.setOnClickListener(v -> getPresenter().postFinishEvent());
    }

    @Override
    public void setData(Wiki wiki) {
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_wiki_face_selected);
        Drawable drawableNormal = getResources().getDrawable(R.mipmap.ic_wiki_face_normal);
        if (wiki.getIs_like().equals("0")) {
            wiki.setIs_like("1");
            drawableNormal.setBounds(0, 0, drawableNormal.getMinimumWidth(), drawableNormal.getMinimumHeight());
            mTvWikiLikeNum.setCompoundDrawables(drawableNormal, null, null, null);
        } else {
            wiki.setIs_like("0");
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvWikiLikeNum.setCompoundDrawables(drawable, null, null, null);
        }
        mTvWikiLikeNum.setOnClickListener(v -> getPresenter().addBaikeLike(wiki));
        mTvWikiLikeNum.setText(wiki.getLike_num() <= 0 ? "" : wiki.getLike_num() + "");
        mTvWikiAskName.setText("Q : " + wiki.getQuestion());
        mTvWikiAskReply.setText("A : " + wiki.getContent());
        if (TextUtils.isEmpty(wiki.getPicture())) {
            mSdvWiki.setVisibility(View.GONE);
        } else {
            mSdvWiki.setImageURI(wiki.getPicture());
        }

        getPresenter().getWikiAdapter().clear();
        getPresenter().getWikiAdapter().addAll(wiki.getRelation_info());
    }

    public void setLike(Wiki wiki) {
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_wiki_face_selected);
        Drawable drawableNormal = getResources().getDrawable(R.mipmap.ic_wiki_face_normal);
        if (wiki.getIs_like().equals("1")) {
            wiki.setIs_like("0");
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvWikiLikeNum.setCompoundDrawables(drawable, null, null, null);
            wiki.setLike_num(wiki.getLike_num() + 1);
            mTvWikiLikeNum.setText((wiki.getLike_num()) + "");
        } else {
            wiki.setIs_like("1");
            drawableNormal.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvWikiLikeNum.setCompoundDrawables(drawableNormal, null, null, null);
            if (wiki.getLike_num() > 0) {
                wiki.setLike_num(wiki.getLike_num() - 1);
                if (wiki.getLike_num() == 0) {
                    mTvWikiLikeNum.setText("");
                } else {
                    mTvWikiLikeNum.setText(wiki.getLike_num() + "");
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String len = "Q : ";
        String name = mTvWikiAskName.getText().toString().substring(len.length(), mTvWikiAskName.getText().length());
        new ShareBottomDialog.Builder(this)
                .setUrl(getPresenter().getData().getShare_url())
                .setTitle("[" + name + "]" + "【颜究院】")
                .setContent("解决你的护肤难题")
                .setWxCircleTitle(name + "【颜究院】")
                .setWbContent(name + "【颜究院】" + getPresenter().getData().getShare_url())
                .show();
        return super.onOptionsItemSelected(item);
    }

}
