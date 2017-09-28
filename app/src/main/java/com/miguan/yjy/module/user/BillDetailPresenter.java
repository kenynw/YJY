package com.miguan.yjy.module.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.dialogs.ShareImageDialog;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Bill;
import com.miguan.yjy.model.bean.EntityRoot;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.ScreenShot;

import java.util.Collections;

import static com.miguan.yjy.model.constant.Constants.EXTRA_BILL_ID;
import static com.miguan.yjy.model.constant.Constants.EXTRA_BILL_NAME;

/**
 * Copyright (c) 2017/9/21. LiaoPeiKun Inc. All rights reserved.
 */

public class BillDetailPresenter extends BaseListActivityPresenter<BillDetailActivity, Bill> {

    private boolean mIsEditMode = false;

    private ItemTouchHelper mItemTouchHelper;

    private int mBillId;

    private String mBillName;

    public static void start(Context context, int billId, String billName) {
        Intent intent = new Intent(context, BillDetailActivity.class);
        intent.putExtra(EXTRA_BILL_ID, billId);
        intent.putExtra(EXTRA_BILL_NAME, billName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(BillDetailActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mBillId = getView().getIntent().getIntExtra(EXTRA_BILL_ID, 0);
        mBillName = getView().getIntent().getStringExtra(EXTRA_BILL_NAME);
    }

    @Override
    protected void onCreateView(BillDetailActivity view) {
        super.onCreateView(view);
        getView().setToolbarTitle(mBillName);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        UserModel.getInstance().getBillDetail(mBillId, 1)
                .map(listEntityList -> {
                    getView().setCount(listEntityList.getPageTotal());
                    return listEntityList.getData();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        UserModel.getInstance().getBillDetail(mBillId, getCurPage())
                .map(EntityRoot::getData)
                .unsafeSubscribe(getMoreSubscriber());
    }

    public void setEditMode(boolean editMode) {
        mIsEditMode = editMode;
    }

    public boolean isEditMode() {
        return mIsEditMode;
    }

    public ItemTouchHelper getItemTouchHelper() {
        if (mItemTouchHelper == null) {
            mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
            mItemTouchHelper.attachToRecyclerView(getView().getListView().getRecyclerView());
        }
        return mItemTouchHelper;
    }

    public void exportToImage() {
        if (getAdapter().getCount() > 0) {
            getView().getExpansionDelegate().showProgressBar("正在保存图片");
            View view = View.inflate(getView(), R.layout.user_export_bill, null);
            RecyclerView rv = view.findViewById(R.id.rv_export_bill_list);
            rv.setLayoutManager(new LinearLayoutManager(getView()));
            rv.addItemDecoration(getView().getDividerDecoration());
            rv.setAdapter(getAdapter());
            TextView tvName = view.findViewById(R.id.tv_export_bill_name);
            tvName.setText(mBillName);
            TextView tvUsername = view.findViewById(R.id.tv_export_bill_username);
            tvUsername.setText("by " + UserPreferences.getUsername() + " from 颜究院");
            ScreenShot.getInstance().takeScreenShotOfJustView(view, Bitmap.Config.ARGB_4444, (path, uri) -> {
                getView().getExpansionDelegate().hideProgressBar();
                ShareImageDialog.newInstance(path, getView())
                        .show(getView().getSupportFragmentManager(), "");
            });
        } else {
            LUtils.toast("还未添加任何产品到清单");
        }
    }

    private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            if (viewHolder.getItemViewType() != target.getItemViewType()) {
                return false;
            }

            Collections.swap(getAdapter().getAllData(), viewHolder.getLayoutPosition(), target.getLayoutPosition());
            getAdapter().notifyItemMoved(viewHolder.getLayoutPosition(), target.getLayoutPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

    }

}
