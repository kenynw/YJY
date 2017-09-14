package com.miguan.yjy.module.product;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.miguan.yjy.adapter.EffectSortAdapter;
import com.miguan.yjy.adapter.ProductSortAdapter;
import com.miguan.yjy.model.bean.Category;
import com.miguan.yjy.model.bean.Effect;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/10. LiaoPeiKun Inc. All rights reserved.
 */

public class SearchFilterPanel implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.ll_product_filter)
    LinearLayout mLlFilter;

    @BindViews({R.id.tbtn_product_filter_all, R.id.tbtn_product_filter_cate, R.id.tbtn_product_filter_effect})
    List<ToggleButton> mTbtnList;

    private ChainBaseActivity mActivity;

    private List<Category> mCategories;

    private List<Effect> mEffects;

    private List<View> mMenuList;

    private PopupWindow mMenuWindow;

    private ToggleButton mCurrentBtn;

    public SearchFilterPanel(ChainBaseActivity activity, List<Category> categories, List<Effect> effects) {
        mActivity = activity;
        mCategories = categories;
        mEffects = effects;
        ButterKnife.bind(this, activity);

        init();
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

    public void setEffects(List<Effect> effects) {
        mEffects = effects;
        mTbtnList.get(2).setEnabled(effects != null && effects.size() > 0);
        effectSortProductAdapter.onlyAddAll(effects);
    }

    public void updateAllData(ProductList product) {
        setEffects(product.getEffectList());
    }

    public List<ToggleButton> getTbtnList() {
        return mTbtnList;
    }

    private void init() {
        mMenuList = new ArrayList<>();

//        String[] categories = new String[mCategories.size()];
//        for (int i = 0; i < mCategories.size(); i++) {
//            categories[i] = mCategories.get(i).getCate_name();
//        }


        String[] effects = new String[mEffects.size()];
        for (int i = 0; i < mEffects.size(); i++) {
            effects[i] = mEffects.get(i).getEffect_name();
        }


            mTbtnList.get(2).setText("功效");
//        if (mEffects == null && mEffects.size() == 0) {
//            mTbtnList.get(2).setEnabled(false);
//        } else {
//            mTbtnList.get(2).setEnabled(true);
//        }

        ButterKnife.apply(mTbtnList, new ButterKnife.Action<ToggleButton>() {
            @Override
            public void apply(@NonNull ToggleButton view, int index) {
                if (index > 0) {
                    view.setTag(index - 1);
                    view.setOnCheckedChangeListener(SearchFilterPanel.this);
                } else {
                    view.setOnClickListener(v -> {
                        mTbtnList.get(1).setText("分类");
                        mTbtnList.get(2).setText("功效");
                        if (mListener != null)
                            mListener.onItemSelected(0, "");
                    });
                }
            }
        });

        mMenuList.add(createSortRecyclerView(mCategories, position -> {
//            mTbtnList.get(1).setText(mCategories[position]);
            for (int i = 0; i < mCategories.size(); i++) {
                mCategories.get(i).setSelect(false);
            }
            mCategories.get(position).setSelect(true);
            secondSortProductAdapter.notifyDataSetChanged();
            if (mListener != null) {
                mListener.onItemSelected(mCategories.get(position).getId(), "");
            }
        }));
        mMenuList.add(createEffectRecyclerView(position -> {
            if (mListener != null) {
                mListener.onItemSelected(0, mEffects.get(position).getEffect_id() + "");
            }
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

    SecondSortProductAdapter secondSortProductAdapter;
    EffectSortAdapter effectSortProductAdapter;

    private View createSortRecyclerView(List<Category> categories, RecyclerArrayAdapter.OnItemClickListener listener) {
        RecyclerView recyclerView = new RecyclerView(mActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        categories.get(0).setSelect(true);
        secondSortProductAdapter = new SecondSortProductAdapter(mActivity, categories);
        secondSortProductAdapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(secondSortProductAdapter);
        recyclerView.setBackgroundColor(mActivity.getResources().getColor(R.color.white));

        RelativeLayout rl = new RelativeLayout(mActivity);
        rl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rl.setOnClickListener(v -> dismissMenu());
        rl.setBackgroundResource(R.color.text_black_secondary);
        rl.addView(recyclerView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return rl;
    }


    private View createEffectRecyclerView(RecyclerArrayAdapter.OnItemClickListener listener) {
        FlowTagLayout recyclerView = new FlowTagLayout(mActivity);
        recyclerView.MAX_LINE = 3;
        recyclerView.setPadding(LUtils.dp2px(20), 0, LUtils.dp2px(20), LUtils.dp2px(-10));
        effectSortProductAdapter = new EffectSortAdapter(mActivity, mEffects);
        effectSortProductAdapter.setSetOnTagClickListener(new EffectSortAdapter.SetOnTagClickListener() {
            @Override
            public void itemClick(View v, int position) {
                if (mListener != null)
                    mTbtnList.get(2).setText(mEffects.get(position).getEffect_name());
                mListener.onItemSelected(0, mEffects.get(position).getEffect_id() + "");
            }
        });
        recyclerView.setAdapter(effectSortProductAdapter);
        recyclerView.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
        effectSortProductAdapter.onlyAddAll(mEffects);

        RelativeLayout rl = new RelativeLayout(mActivity);
        rl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rl.setOnClickListener(v -> dismissMenu());
        rl.setBackgroundResource(R.color.text_black_secondary);
        rl.addView(recyclerView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        rl.setPadding(LUtils.dp2px(20),LUtils.dp2px(15),LUtils.dp2px(15),LUtils.dp2px(20));

        return rl;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        dismissMenu();

        if (mListener != null) {
            mListener.onMenuCheck();
        }

        if (isChecked && buttonView instanceof ToggleButton) {
            showMenu((Integer) buttonView.getTag());
            mCurrentBtn = (ToggleButton) buttonView;
        }
    }

    public void showMenu(int position) {
        if (mMenuWindow == null) {
            mMenuWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mMenuWindow.setFocusable(false);
            mMenuWindow.setAnimationStyle(R.style.PopupWindowPushUpAnimation);
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

    public void setMenuText(int index, String text) {
        if (index > 0 && index < 3 && !text.isEmpty())
            mTbtnList.get(index).setText(text);
    }

    private OnItemSelectedListener mListener;

    public interface OnItemSelectedListener {
        void onItemSelected(int id, String text);

        void onMenuCheck();

        void onSencondItemSelected(int id, String text, List<Effect> effectList);
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
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void setData(String data) {
                mTvProductSortName.setText(data);
            }
        }

    }

    public void setVisibility(int visibility) {
        mLlFilter.setVisibility(visibility);
    }

    class SecondSortProductAdapter extends RecyclerArrayAdapter<Category> {

        public SecondSortProductAdapter(Context context, List<Category> objects) {
            super(context, objects);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new SecondSortViewHolder(parent);
        }

        class SecondSortViewHolder extends BaseViewHolder<Category> {

            @BindView(R.id.tv_sort_first_name)
            TextView mTvSortFirstName;
            @BindView(R.id.flowtag_sencond_sort)
            FlowTagLayout mFlowtagSencondSort;

            public SecondSortViewHolder(ViewGroup parent) {
                super(parent, R.layout.search_second_sort);
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void setData(Category data) {
                super.setData(data);
                mTvSortFirstName.setText(data.getCate_name());
                if (data.isSelect()) {
                    mTvSortFirstName.setCompoundDrawables(null, null, null, null);
                    ProductSortAdapter mProductSortAdapter = new ProductSortAdapter(mActivity, data.getSub());
                    mFlowtagSencondSort.setFocusable(false);
                    mFlowtagSencondSort.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                    mFlowtagSencondSort.setAdapter(mProductSortAdapter);
                    mProductSortAdapter.onlyAddAll(data.getSub());
                    mProductSortAdapter.setSetOnTagClickListener(new ProductSortAdapter.SetOnTagClickListener() {
                        @Override
                        public void itemClick(View v, int position) {
                            effectSortProductAdapter.onlyAddAll(mEffects);
//                            if (mEffects.size() == 0) {
//                                mTbtnList.get(2).setText("功效");
//                                mTbtnList.get(2).setClickable(false);
//                            } else {
//                                mTbtnList.get(2).setClickable(true);
//                            }
                            mTbtnList.get(1).setText(data.getSub().get(position).getCate_name());
                            if (mListener != null)
                                mListener.onSencondItemSelected(
                                        data.getSub().get(position).getId(),
                                        data.getSub().get(position).getCate_name(),
                                        mEffects);
                        }
                    });
                } else {
                    Drawable drawable = mActivity.getResources().getDrawable(R.mipmap.ic_product_detail_record_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mTvSortFirstName.setCompoundDrawables(null, null, drawable, null);
                    ProductSortAdapter mProductSortAdapter = new ProductSortAdapter(mActivity, null);
                    mFlowtagSencondSort.setFocusable(false);
                    mFlowtagSencondSort.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                    mFlowtagSencondSort.setAdapter(mProductSortAdapter);
                }

            }
        }
    }


}
