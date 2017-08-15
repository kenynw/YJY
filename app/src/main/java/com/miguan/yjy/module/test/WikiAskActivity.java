package com.miguan.yjy.module.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.WikiViewHolder;
import com.miguan.yjy.module.user.FeedbackActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.SharePopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/7/25 11:49
 * @描述
 */
@RequiresPresenter(WikiAskActivityPresenter.class)
public class WikiAskActivity extends BaseListActivity<WikiAskActivityPresenter> {
    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_toolbar)
    LinearLayout mLlToolbar;
    @BindView(R.id.tv_wiki_ask_name)
    TextView mTvWikiAskName;
    @BindView(R.id.tv_wiki_ask_reply)
    TextView mTvWikiAskReply;
    @BindView(R.id.recycle)
    EasyRecyclerView mRecycle;
    @BindView(R.id.tv_wiki_ask_remark)
    TextView mTvWikiAskRemark;

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new WikiViewHolder(parent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarTitle("帮助");
        mTvWikiAskRemark.setOnClickListener(v -> startActivity(new Intent(this, FeedbackActivity.class)));
    }

    @Override
    protected int getLayout() {
        return R.layout.wiki_activity_detail;
    }

    @Override
    public ListConfig getListConfig() {
        DividerDecoration decoration = new DividerDecoration(
                getResources().getColor(R.color.fed),
                LUtils.dp2px(1),
                LUtils.dp2px(28),
                LUtils.dp2px(28)
        );
        return super.getListConfig()
                .setItemDecoration(decoration)
                .setLoadMoreAble(false)
                .setRefreshAble(false)
                .setNoMoreAble(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        new SharePopupWindow.Builder(this)
                .setUrl(getPresenter().share_url)
                .setTitle(mTvWikiAskName.getText() + "【颜究院】")
                .setContent("解决你的护肤难题")
                .setWxCircleTitle(mTvWikiAskName.getText() + "【颜究院】")
                .setWbContent(mTvWikiAskName.getText() + "【颜究院】" + getPresenter().share_url)
                .show(getToolbar());
        return super.onOptionsItemSelected(item);
    }
}
