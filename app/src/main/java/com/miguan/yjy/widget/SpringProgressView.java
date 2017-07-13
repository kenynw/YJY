package com.miguan.yjy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.miguan.yjy.R;

/**
 * @作者 cjh
 * @日期 2017/6/16 11:10
 * @描述 自定义进度条
 */
public class SpringProgressView extends View {

    public final String TAG = SpringProgressView.this.getClass().getName();
    private int finalColor;

    /**
     * 分段颜色
     */
    private int[] SECTION_COLORS = {getContext().getResources().getColor(R.color.first_skin), getContext().getResources().getColor(R.color.sencond_skin), getContext().getResources().getColor(R.color.third_skin)};
//    private  int[] SECTION_COLORS = {Color.GREEN, Color.YELLOW, Color.RED};
    /**
     * 进度条最大值
     */
    private float maxCount;
    /**
     * 进度条当前值
     */
    private float currentCount;
    /**
     * 画笔
     */
    private Paint mPaint;

    private Paint mTextPaint;//文本画笔
    private int mWidth, mHeight;
    public static int LAST_WIDTH;

    public SpringProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SpringProgressView(Context context) {
        super(context, null);
        initView(context);
    }

    private void initView(Context context) {
    }


    public SpringProgressView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mTextPaint = new Paint();////消除锯齿
        mTextPaint.setAntiAlias(true);
        mPaint.setAntiAlias(true);
        int round = mHeight / 2;
        Log.e(TAG, "max=" + maxCount + "  current=" + currentCount);
        mTextPaint.setColor(Color.rgb(0, 0, 0));
        mPaint.setColor(Color.rgb(239, 239, 239));
        RectF rectBg = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectBg, round, round, mPaint);
        mPaint.setColor(getContext().getResources().getColor(R.color.f5));
        RectF rectBlackBg = new RectF(2, 2, mWidth - 2, mHeight - 2);
        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);

        float section = currentCount / maxCount;
        RectF rectProgressBg = new RectF(2.5f, 2.5f, (mWidth - 2.5f) * section, mHeight - 2.5f);
        if (section <= 1.0f / 3.0f) {
            if (section != 0.0f) {
                finalColor = SECTION_COLORS[0];
                mPaint.setColor(SECTION_COLORS[0]);
            } else {
                mPaint.setColor(Color.TRANSPARENT);
            }
        } else {
            int count = (section <= 1.0f / 3.0f * 2) ? 2 : 3;
            int[] colors = new int[count];
            System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
            float[] positions = new float[count];
            if (count == 2) {
                positions[0] = 0.0f;
                positions[1] = 1.0f - positions[0];
            } else {
                positions[0] = 0.0f;
                positions[1] = (maxCount / 3) / currentCount;
                positions[2] = 1.0f - positions[0] * 2;
            }
            positions[positions.length - 1] = 1.0f;
            LinearGradient shader = new LinearGradient(2, 2, (mWidth - 2) * section, mHeight - 2, colors, null, Shader.TileMode.MIRROR);
            mPaint.setShader(shader);
            finalColor = colors[colors.length-1];
        }
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
        int[] location = new int[2];
        this.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (getLocationOnScreenXy != null) {
            getLocationOnScreenXy.getXy((int)(x+mWidth*section),finalColor);
        }
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /***
     * 设置最大的进度值
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /***
     * 设置当前的进度值
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    public interface GetLocationOnScreenXy {
        void getXy(int x,int color);
    }

    GetLocationOnScreenXy getLocationOnScreenXy;

    public void setGetLocationOnScreenXy(GetLocationOnScreenXy getLocationOnScreenXy) {
        this.getLocationOnScreenXy = getLocationOnScreenXy;
    }
}