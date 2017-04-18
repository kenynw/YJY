package com.miguan.yjy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */

public class ScrollIndexer extends View {

    private String[] letters = new String[]{"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z", "#"};

    private Paint mPaint;

    private Rect mBound;

    private int mWidth;

    private int mHeight;

    private int mTouchSlop;

    private boolean mIsMove = false;

    public ScrollIndexer(Context context) {
        this(context, null);
    }

    public ScrollIndexer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollIndexer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xff666666);
        mPaint.setTextSize(32);
        mBound = new Rect();

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth() / 2;
        mHeight = getMeasuredHeight() / letters.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i=0; i<letters.length; i++) {
            String text = String.valueOf(letters[i]);
            mPaint.getTextBounds(text, 0, text.length(), mBound);
            canvas.drawText(text, mWidth - mBound.width() / 2, mHeight * (i + 1), mPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int downY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE :
                int dy = (int) (event.getY() - downY);
                if (dy > mTouchSlop) {
                    mIsMove = true;
                }
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN :
                setBackgroundColor(0x33000000);
                break;
            case MotionEvent.ACTION_MOVE :
                if (mIsMove) {
                    int position = y / mHeight;
                    if (position > letters.length -1) position = letters.length - 1;
                    if (position < 0) position = 0;
                    if (mListener != null) {
                        mListener.onTextSelected(position, String.valueOf(letters[position]));
                    }
                }
                break;
            case MotionEvent.ACTION_UP :
                setBackgroundResource(android.R.color.transparent);
                if (mListener != null) mListener.onTouchUp();
                break;
        }

        return true;
    }

    private OnTextSelectedListener mListener;

    public interface OnTextSelectedListener {
        void onTextSelected(int position, String text);
        void onTouchUp();
    }

    public void setOnTextSelectedListener(OnTextSelectedListener listener) {
        mListener = listener;
    }

}
