package com.miguan.yjy.module.product;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Category;
import com.miguan.yjy.utils.LUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/10. LiaoPeiKun Inc. All rights reserved.
 */

public class FilterPanel implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.ll_product_filter)
    LinearLayout mLlFilter;

    @BindViews({R.id.tbtn_product_filter_all, R.id.tbtn_product_filter_cate, R.id.tbtn_product_filter_effect})
    List<ToggleButton> mTbtnList;

    private ChainBaseActivity mActivity;

    private List<Category> mCategories;

    private String[] mEffects;

    private List<View> mMenuList;

    private PopupWindow mMenuWindow;

    private ToggleButton mCurrentBtn;

    public FilterPanel(ChainBaseActivity activity, List<Category> categories, String[] effects) {
        mActivity = activity;
        mCategories = categories;
        mEffects = effects;
        ButterKnife.bind(this, activity);

        init();
    }

    private void init() {
        mMenuList = new ArrayList<>();

        String[] categories = new String[mCategories.size()];
        for (int i = 0; i < mCategories.size(); i++) {
            categories[i] = mCategories.get(i).getCate_name();
        }

        ButterKnife.apply(mTbtnList, new ButterKnife.Action<ToggleButton>() {
            @Override
            public void apply(@NonNull ToggleButton view, int index) {
                if (index > 0) {
                    String[] strings = index == 1 ? categories : mEffects;
                    view.setTag(index - 1);
                    view.setOnCheckedChangeListener(FilterPanel.this);
                } else {
                    view.setOnClickListener(v -> {
                        mTbtnList.get(1).setText("分类");
                        mTbtnList.get(2).setText("功效");
                        mListener.onItemSelected(0, "");
                    });
                }
            }
        });

        mMenuList.add(createRecyclerView(categories, position -> {
            mTbtnList.get(1).setText(categories[position]);
            mListener.onItemSelected(mCategories.get(position).getId(), categories[position]);
        }));
        mMenuList.add(createRecyclerView(mEffects, position -> {
            mTbtnList.get(2).setText(mEffects[position]);
            mListener.onItemSelected(position, mEffects[position]);
        }));
    }

    private View createRecyclerView(String[] strings, RecyclerArrayAdapter.OnItemClickListener listener) {
        RecyclerView recyclerView = new RecyclerView(mActivity);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        TextArrayAdapter adapter = new TextArrayAdapter(mActivity, strings);
        adapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(mActivity.getResources().getColor(R.color.white));

        RelativeLayout rl = new RelativeLayout(mActivity);
        rl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rl.setOnClickListener(v -> dismissMenu());
        rl.setBackgroundResource(R.color.text_black_secondary);
        rl.addView(recyclerView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return rl;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        dismissMenu();

        if (isChecked && buttonView instanceof ToggleButton) {
            showMenu((Integer) buttonView.getTag());
            mCurrentBtn = (ToggleButton) buttonView;
        }
    }

    public void showMenu(int position) {
        if (mMenuWindow == null) {
            mMenuWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mMenuWindow.setFocusable(false);
            mMenuWindow.setOutsideTouchable(true);
        }

        mMenuWindow.setContentView(mMenuList.get(position));
        if (Build.VERSION.SDK_INT < 24) {
            mMenuWindow.showAsDropDown(mLlFilter);
        } else {
            int[] location = new int[2];
            mLlFilter.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            mMenuWindow.setHeight(LUtils.getScreenHeight() - y - mLlFilter.getHeight());
            mMenuWindow.showAtLocation(mLlFilter, Gravity.NO_GRAVITY, 0, y + mLlFilter.getHeight());
        }
    }

    public boolean dismissMenu() {
        if (mMenuWindow != null && mMenuWindow.isShowing()) {
            mMenuWindow.dismiss();
            if (mCurrentBtn != null) mCurrentBtn.setChecked(false);
            return true;
        }
        return false;
    }

    private OnItemSelectedListener mListener;

    public interface OnItemSelectedListener {
        void onItemSelected(int id, String text);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mListener = listener;
    }

    class TextArrayAdapter extends RecyclerArrayAdapter<String> {

        public TextArrayAdapter(Context context, String[] objects) {
            super(context, objects);
        }

        @Override
        public TextViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new TextViewHolder(View.inflate(mActivity, R.layout.item_product_name, null));
        }

        @Override
        public void OnBindViewHolder(BaseViewHolder holder, int position) {
            holder.setData(mObjects.get(position));
        }

        class TextViewHolder extends BaseViewHolder<String> {

            @BindView(R.id.tv_product_sort_name)
            TextView mTvProductSortName;

            public TextViewHolder(View itemView) {
                super(itemView);
                ButterKnife. bind(this, itemView);
            }

            @Override
            public void setData(String data) {
                mTvProductSortName.setText(data);
            }
        }

    }

}
