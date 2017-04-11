package com.miguan.yjy.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ToggleButton;

import com.miguan.yjy.R;

import java.util.List;


/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class ExpandTabView extends LinearLayout implements OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String[] BUTTON_TEXTS = new String[] {"全部", "销量", "浏览量", "价格"};

    List<String> mCateList;

    private static final String[][] MENU_ARRAY = new String[][] {
            { "销量低到高", "销量高到低" },
            { "浏览量低到高", "浏览量高到低" },
            { "价格低到高", "价格高到低" }
    };

    private Context mContext;

    /**
     * 屏幕尺寸
     */
    private Point mDisplaySize;

    /**
     * 弹出窗口
     */
    private PopupWindow mPopupWindow;

    /**
     * 保存所有按钮
     */
    private List<ToggleButton> mToggleBtnList;

    /**
     * 所有下拉菜单
     */
    private List<View> mMenuList;

    private List<ArrayAdapter<String>> mAdapterList;

    private ToggleButton mCurrentBtn;

    public ExpandTabView(Context context) {
        this(context, null);
    }

    public ExpandTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;

        init(context);

        initView();
    }

    private void init(Context context) {
        mContext = context;

        // 获取屏幕尺寸
        mDisplaySize = new Point();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getSize(mDisplaySize);

    }

    private void initView() {
        this.setOrientation(HORIZONTAL);
        this.setBackgroundResource(R.color.white);

    }

    private void showMenu(int position) {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mDisplaySize.x, mDisplaySize.y);
//            mPopupWindow.setAnimationStyle(R.style.PopupWindowPushUpAnimation);
            mPopupWindow.setFocusable(false);
            mPopupWindow.setOutsideTouchable(true);
        }

        mPopupWindow.setContentView(mMenuList.get(position));
        mPopupWindow.showAsDropDown(this, 0, 0);
    }

    public boolean dismissMenu() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            if (mCurrentBtn != null) mCurrentBtn.setChecked(false);
            return true;
        }
        return false;
    }

    public void setData(int position, String[] data) {
        if (data == null || mAdapterList == null) return;
        ArrayAdapter<String> adapter = mAdapterList.get(position);
        for (String name : data) {
            adapter.add(name);
        }
        adapter.notifyDataSetChanged();
    }

    public void setText(int position, String text) {
        mToggleBtnList.get(position).setText(text);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        dismissMenu();

        if (isChecked && buttonView instanceof ToggleButton) {
            showMenu((Integer) buttonView.getTag());
            mCurrentBtn = (ToggleButton) buttonView;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismissMenu();

        String text = (String) parent.getAdapter().getItem(position);
        mCurrentBtn.setText(text);

        if (mListener != null) {
            mListener.onItemSelected((Integer) mCurrentBtn.getTag(), position);
        }
    }

    @Override
    public void onClick(View v) {
        dismissMenu();
    }

    private OnItemSelectedListener mListener;

    public interface OnItemSelectedListener {
        void onItemSelected(int position, int selected);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mListener = listener;
    }

}
