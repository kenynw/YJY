package com.miguan.yjy.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.miguan.yjy.R;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class CirclePageIndicator extends View implements ViewPager.OnPageChangeListener {
    private static final int INVALID_POINTER = -1;
    // ============================================
    // ===================Config===================
    // ============================================
    // 线条粗细,单位dp
    private static final int STROKE_WIDTH = 1;
    // 半径,单位dp
    private static final int CIRCLE_RADIUS = 3;
    // 线条间隔,单位dp
    private static final int GAP_WIDTH = 8;
    // ============================================
    // =================Config End=================
    // ============================================

    // 没有选中的时候的画笔
    private final Paint mPaintUnselected = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Paint mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);

    // 选中的时候的画笔
    private final Paint mPaintSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;
    // 当前页面
    private int mCurrentPage;
    // 是否居中
    private boolean mCentered = true;
    // 半径,单位px
    private float mRadius;
    // 线条之间的间隔,单位px
    private float mGapWidth;

    private int mTouchSlop;
    private float mLastMotionX = -1;
    private int mActivePointerId = INVALID_POINTER;
    private boolean mIsDragging;

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    /**
     * dp转px
     *
     * @param res
     *            android.content.res.Resources
     * @param dp
     * @return
     */
    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public CirclePageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode())
            return;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator);

        final Resources res = getResources();
        mRadius = dpToPx(res, CIRCLE_RADIUS);
        mGapWidth = dpToPx(res, GAP_WIDTH);

        mPaintSelected.setStyle(Paint.Style.FILL);
        mPaintSelected.setColor(ta.getColor(R.styleable.CirclePageIndicator_selected_color, 0xfaffffff));

        mPaintUnselected.setStyle(Paint.Style.FILL);
        mPaintUnselected.setColor(ta.getColor(R.styleable.CirclePageIndicator_unselected_color, 0x8affffff));

        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setStrokeWidth(ta.getDimension(R.styleable.CirclePageIndicator_strokeWidth, dpToPx(res, STROKE_WIDTH)));
        mPaintStroke.setColor(ta.getColor(R.styleable.CirclePageIndicator_strokeColor, 0x00000000));
        ta.recycle();

        mTouchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
    }

    public void setCentered(boolean centered) {
        mCentered = centered;
        invalidate();
    }

    public boolean isCentered() {
        return mCentered;
    }

    public void setUnselectedColor(int unselectedColor) {
        mPaintUnselected.setColor(unselectedColor);
        invalidate();
    }

    public int getUnselectedColor() {
        return mPaintUnselected.getColor();
    }

    public void setSelectedColor(int selectedColor) {
        mPaintSelected.setColor(selectedColor);
        invalidate();
    }

    public int getSelectedColor() {
        return mPaintSelected.getColor();
    }

    public void setRadius(float radius) {
        mRadius = radius;
        invalidate();
    }

    public float getRadius() {
        return mRadius;
    }

    public void setStrokeWidth(float lineHeight) {

        invalidate();
    }

    public float getStrokeWidth() {
        return mPaintSelected.getStrokeWidth();
    }

    public void setGapWidth(float gapWidth) {
        mGapWidth = gapWidth;
        invalidate();
    }

    public float getGapWidth() {
        return mGapWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mViewPager == null) {
            return;
        }
        final int count = mViewPager.getAdapter().getCount();
        if (count == 0) {
            return;
        }

        if (mCurrentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }

        final float lineWidthAndGap = mRadius + mGapWidth;
        final float indicatorWidth = (count * lineWidthAndGap) - mGapWidth;
        final float paddingTop = getPaddingTop();
        final float paddingLeft = getPaddingLeft();
        final float paddingRight = getPaddingRight();

        float verticalOffset = paddingTop + ((getHeight() - paddingTop - getPaddingBottom()) / 2.0f);
        float horizontalOffset = paddingLeft;
        if (mCentered) {
            horizontalOffset += ((getWidth() - paddingLeft - paddingRight) / 2.0f) - (indicatorWidth / 2.0f);
        }

        // Draw stroked circles
        for (int i = 0; i < count; i++) {
            float dx1 = horizontalOffset + (i * lineWidthAndGap);
            canvas.drawCircle(dx1, verticalOffset, mRadius, (i == mCurrentPage) ? mPaintSelected : mPaintUnselected);
            canvas.drawCircle(dx1, verticalOffset, mRadius, mPaintStroke);
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (super.onTouchEvent(ev)) {
            return true;
        }
        if ((mViewPager == null) || (mViewPager.getAdapter().getCount() == 0)) {
            return false;
        }

        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mLastMotionX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE: {
                final int activePointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                final float x = MotionEventCompat.getX(ev, activePointerIndex);
                final float deltaX = x - mLastMotionX;

                if (!mIsDragging) {
                    if (Math.abs(deltaX) > mTouchSlop) {
                        mIsDragging = true;
                    }
                }

                if (mIsDragging) {
                    mLastMotionX = x;
                    if (mViewPager.isFakeDragging() || mViewPager.beginFakeDrag()) {
                        mViewPager.fakeDragBy(deltaX);
                    }
                }

                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!mIsDragging) {
                    final int count = mViewPager.getAdapter().getCount();
                    final int width = getWidth();
                    final float halfWidth = width / 2f;
                    final float sixthWidth = width / 6f;

                    if ((mCurrentPage > 0) && (ev.getX() < halfWidth - sixthWidth)) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            mViewPager.setCurrentItem(mCurrentPage - 1);
                        }
                        return true;
                    } else if ((mCurrentPage < count - 1) && (ev.getX() > halfWidth + sixthWidth)) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            mViewPager.setCurrentItem(mCurrentPage + 1);
                        }
                        return true;
                    }
                }

                mIsDragging = false;
                mActivePointerId = INVALID_POINTER;
                if (mViewPager.isFakeDragging())
                    mViewPager.endFakeDrag();
                break;

            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(ev);
                mLastMotionX = MotionEventCompat.getX(ev, index);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }
                mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, mActivePointerId));
                break;
        }

        return true;
    }

    public void setViewPager(ViewPager viewPager) {
        if (mViewPager == viewPager) {
            return;
        }
        if (mViewPager != null) {
            // Clear us from the old pager.
            mViewPager.addOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        mCurrentPage = mViewPager.getCurrentItem();
        invalidate();
    }

    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mViewPager.setCurrentItem(item);
        mCurrentPage = item;
        invalidate();
    }

    public void notifyDataSetChanged() {
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPage = position;
        invalidate();

        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (mViewPager == null)) {
            // We were told how big to be
            result = specSize;
        } else {
            // Calculate the width according the views count
            final int count = mViewPager.getAdapter().getCount();
            result = getPaddingLeft() + getPaddingRight() + (count * mRadius) + ((count - 1) * mGapWidth);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return (int) Math.ceil(result);
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the height
            result = mPaintSelected.getStrokeWidth() + getPaddingTop() + getPaddingBottom();
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return (int) Math.ceil(result);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
