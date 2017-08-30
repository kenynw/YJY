package com.miguan.yjy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.product.BenefitListActivity;
import com.miguan.yjy.module.product.QueryCodeActivity;
import com.miguan.yjy.module.user.UsedListActivity;
import com.miguan.yjy.widget.LoadingTextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/8/28. LiaoPeiKun Inc. All rights reserved.
 */

public class HomeHeaderAdapter extends BaseAdapter implements View.OnClickListener {

    private final int[] mIcons = new int[]{
            R.mipmap.ic_home_product, R.mipmap.ic_home_used,
            R.mipmap.ic_home_batch, R.mipmap.ic_home_benefits
    };

    private final String[] mTitles;

    private final String[] mDescs;

    private Context mContext;

    private boolean mIsLoading = true;

    public HomeHeaderAdapter(Context context) {
        mContext = context;
        mTitles = mContext.getResources().getStringArray(R.array.text_home_buttons);
        mDescs = mContext.getResources().getStringArray(R.array.text_home_buttons_desc);
    }

    public void setLoading(Boolean isLoading) {
        mIsLoading = isLoading;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(parent);
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!mIsLoading) {
            holder.mIvHomeIcon.setImageResource(mIcons[position]);
            holder.mTvHomeTitle.setText(mTitles[position]);
            holder.mTvHomeDesc.setText(mDescs[position]);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);
        }

        return holder.itemView;
    }

    @Override
    public void onClick(View v) {
        switch ((Integer) v.getTag()) {
            case 0:
                EventBus.getDefault().post(1);
                break;
            case 1:
                Class<?> cls = AccountModel.getInstance().isLogin() ? UsedListActivity.class : LoginActivity.class;
                Intent intent = new Intent(mContext, cls);
                mContext.startActivity(intent);
                break;
            case 2:
                mContext.startActivity(new Intent(mContext, QueryCodeActivity.class));
                break;
            case 3:
                mContext.startActivity(new Intent(mContext, BenefitListActivity.class));
                break;
        }
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_home_icon)
        ImageView mIvHomeIcon;

        @BindView(R.id.tv_home_title)
        LoadingTextView mTvHomeTitle;

        @BindView(R.id.tv_home_desc)
        LoadingTextView mTvHomeDesc;

        @BindView(R.id.rl_home_batch)
        RelativeLayout mRlHomeBatch;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_grid_home_button);
            ButterKnife.bind(this, itemView);
        }

    }

}
