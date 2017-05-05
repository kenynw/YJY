package com.miguan.yjy.module.template;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.ScreenShot;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(GenTemplatePresenter.class)
public class GenTemplateActivity extends ChainBaseActivity<GenTemplatePresenter> implements View.OnClickListener {

    @BindView(R.id.fl_template_gen_add)
    FrameLayout mFlAdd;

    @BindView(R.id.rcv_template_gen_list)
    RecyclerView mRcvList;

    @BindView(R.id.tv_template_delete)
    TextView mTvDelete;

    @BindView(R.id.fl_template_gen_delete)
    FrameLayout mFlDelete;

    private List<BaseTemplateViewHolder> mViewHolders;

    private boolean mIsDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_gen);
        setToolbarTitle(R.string.text_title_template);
        ButterKnife.bind(this);

        EasyRecyclerView.DEBUG = true;

        mViewHolders = new ArrayList<>();

        mToolbar.setNavigationOnClickListener(v -> new AlertDialog.Builder(GenTemplateActivity.this)
                .setMessage("确认退出模板")
                .setNegativeButton("取消", null)
                .setPositiveButton("退出", (dialog, which) -> finish()).show());
        mRcvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRcvList.setAdapter(getPresenter().getAdapter());
        mRcvList.setHasFixedSize(true);
        mFlAdd.setOnClickListener(this);
        mFlDelete.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getExpansionDelegate().showProgressBar("正在生成图片");

        mRcvList.setFocusable(true);
        mRcvList.setFocusableInTouchMode(true);
        for (BaseTemplateViewHolder viewHolder : mViewHolders) {
            viewHolder.hideOperatingViews();
        }
        ScreenShot.getInstance().takeScreenShotOfJustView(mRcvList, (path, uri) -> {
            getExpansionDelegate().hideProgressBar();
            SaveTemplatePresenter.start(GenTemplateActivity.this, path, uri);
            finish();
        });

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        GenTemplatePresenter.TemplateAdapter adapter = getPresenter().getAdapter();
        if (adapter.getCount() >= 3) {
            LUtils.toast("最多只能添加3个");
            return;
        }
        adapter.add(new Product());

//        if (v.getId() == R.id.fl_template_gen_add) {
//
//        Product product = new Product();
//        product.setProduct_name("aldflksadjflkajsfd");
//        if (adapter.getCount() > 3) {
//            mRcvList.scrollToPosition(1);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (adapter.getCount() >= 5) {
//                        LUtils.toast("最多只能添加5个");
//                        return;
//                    }
//
//                }
//            }, 500);
//        } else {
//            adapter.add(new Product());
//        }

//        } else if (v.getId() == R.id.fl_template_gen_delete) {
//            if (adapter.getCount() <= 1) {
//                LUtils.toast("至少要保留一个吧");
//                return;
//            }
//            mViewHolders.get(adapter.getCount() - 1).setIsRecyclable(false);
//            mViewHolders.remove(adapter.getCount() - 1);
//            adapter.remove(adapter.getCount() - 1);
//        }
//        mFlDelete.setVisibility(adapter.getCount() > 1 ? View.VISIBLE : View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) // 删除模板
    public void deleteItem(BaseTemplateViewHolder.TemplateDeleteEvent event) {
        GenTemplatePresenter.TemplateAdapter adapter = getPresenter().getAdapter();
        if (adapter.getCount() <= 1) {
            LUtils.toast("至少要保留一个吧");
            return;
        }

        adapter.remove(event.getPosition());
//        LUtils.toast("pos: " + event.getPosition());

//        mIsDelete = true;
//        if (event.getPosition() >= 3) {
//            mRcvList.setOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                    if (newState == SCROLL_STATE_IDLE && mIsDelete && event.getPosition() > 0 && event.getPosition() < adapter.getCount()) {
//                        adapter.remove(event.getPosition());
//                    if (event.getPosition() != adapter.getCount() - 1) {
//                        adapter.notifyItemRangeChanged(event.getPosition(), adapter.getData().size() - event.getPosition());
//                    } else {
//                        adapter.notifyItemRangeChanged(0, adapter.getData().size());
//                    }
//                        mIsDelete = false;
//                    }
//                }
//            });
//
//            mRcvList.scrollToPosition(event.getPosition() - 1);
//        } else {
//            adapter.notifyItemRangeChanged(event.getPosition(), adapter.getData().size() - 1);
//        }

//        adapter.getData().remove(event.getPosition());
//        adapter.notifyItemRemoved(event.getPosition());
//        adapter.notifyDataSetChanged();
//        adapter.notifyItemRangeChanged(0, adapter.getCount() - 1);
//        adapter.notifyItemRangeRemoved(2, 1);
//        if (event.getPosition() != adapter.getCount() - 1) {
//            adapter.notifyItemRangeChanged(event.getPosition(), adapter.getData().size() - event.getPosition());
//        } else {
//            adapter.notifyItemRangeChanged(0, adapter.getData().size());
//        }
//        for (BaseTemplateViewHolder viewHolder : mViewHolders) {
//            adapter.onBindViewHolder(viewHolder, viewHolder.getAdapterPosition());
//        }
    }

    public List<BaseTemplateViewHolder> getViewHolders() {
        return mViewHolders;
    }
}
