package com.miguan.yjy.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class CustomNestedScrollView extends NestedScrollView {

    private int downY;

    private int mTouchSlop;

    private GestureDetector mGestureDetector;

    public CustomNestedScrollView(Context context) {
        super(context);
        if (isInEditMode()) return;
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) return;
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e) && mGestureDetector.onTouchEvent(e);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }
}
