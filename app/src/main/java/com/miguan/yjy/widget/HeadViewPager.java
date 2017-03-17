package com.miguan.yjy.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class HeadViewPager extends ViewPager implements Runnable {

    private static final int POST_DELAYED_TIME = 1000 * 5;

    // 手指是否放在上面
    private boolean mIsTouching;

    // 更新数据需要获得myPagerAdapter
    private PagerAdapter mMyPagerAdapter;

    private PagerAdapter mPagerAdapter;

    public HeadViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;

        postDelayed(this, POST_DELAYED_TIME);
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener == null ? null : new MyOnPageChangeListener(listener));
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mMyPagerAdapter = adapter == null ? null : new MyPagerAdapter(adapter);
        super.setAdapter(mMyPagerAdapter);

        if (adapter != null && adapter.getCount() != 0) {
            setCurrentItem(0, false);
        }
        this.mPagerAdapter = adapter;
    }

    @Override
    // 兼容PageIndicator
    public PagerAdapter getAdapter() {
        return mPagerAdapter;
    }

    public PagerAdapter getMyPagerAdapter() {
        return mMyPagerAdapter;
    }

    @Override
    public int getCurrentItem() {
        return super.getCurrentItem() - 1;
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item + 1, smoothScroll);
    }

    @Override
    // 自动滚动关键
    public void run() {
        if (getAdapter() != null && getAdapter().getCount() > 1 && !mIsTouching) {
            setCurrentItem(getCurrentItem() + 1, true);
        }
        postDelayed(this, POST_DELAYED_TIME);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.mIsTouching = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP
                || event.getAction() == MotionEvent.ACTION_CANCEL) {
            this.mIsTouching = false;
        }

        return super.onTouchEvent(event);
    }

    // 对setAdapter的数据进行包装
    private class MyPagerAdapter extends PagerAdapter {
        private PagerAdapter pa;

        public MyPagerAdapter(PagerAdapter pa) {
            this.pa = pa;
        }

        @Override
        // 关键之一:修改Count长度
        public int getCount() {
            return pa.getCount() > 1 ? pa.getCount() + 2 : pa.getCount();
        }

        @Override
        // 这里是关键之二:修改索引(如果不考虑内容问题可以全部加载进数组然后操作更简单)
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                return pa.instantiateItem(container, pa.getCount() - 1);
            } else if (position == pa.getCount() + 1) {
                return pa.instantiateItem(container, 0);
            } else {
                return pa.instantiateItem(container, position - 1);
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            pa.destroyItem(container, position, object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return pa.isViewFromObject(arg0, arg1);
        }
    }

    // 包装setOnPageChangeListener的数据
    private class MyOnPageChangeListener implements OnPageChangeListener {
        private OnPageChangeListener listener;
        // 是否已经提前触发了OnPageSelected事件
        private boolean alreadyTriggerOnPageSelected;

        public MyOnPageChangeListener(OnPageChangeListener listener) {
            this.listener = listener;
        }

        @Override
        // 关键之三:
        public void onPageScrollStateChanged(int state) {
            if (state == SCROLL_STATE_IDLE) {
                if (getCurrentItem() + 1 == 0) {
                    setCurrentItem(mPagerAdapter.getCount() - 1, false);
                } else if (getCurrentItem() + 1 == mPagerAdapter.getCount() + 1) {
                    setCurrentItem(0, false);
                }
            }

            listener.onPageScrollStateChanged(state);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        // 关键四:
        public void onPageSelected(int position) {
            if (position == 0) {
                listener.onPageSelected(mPagerAdapter.getCount() - 1);
                alreadyTriggerOnPageSelected = true;
            } else if (position == mPagerAdapter.getCount() + 1) {
                listener.onPageSelected(0);
                alreadyTriggerOnPageSelected = true;
            } else {
                if (!alreadyTriggerOnPageSelected) {
                    listener.onPageSelected(position - 1);
                }
                alreadyTriggerOnPageSelected = false;
            }

        }

    }

}
