package com.miguan.yjy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/6/8. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplateTextView extends ClearEditText {

    public TemplateTextView(Context context) {
        super(context);
    }

    public TemplateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TemplateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setAntiAlias(false);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(getWidth() - LUtils.dp2px(36), 0);
        path.lineTo(getWidth(), LUtils.dp2px(36));
        path.lineTo(getWidth(), getHeight());
        path.lineTo(LUtils.dp2px(36), getHeight());
        path.lineTo(0, getHeight() - LUtils.dp2px(36));
        path.lineTo(0, 0);
        canvas.drawPath(path, paint);

        Paint paintDash = new Paint();
        paintDash.reset();
        paintDash.setStyle(Paint.Style.STROKE);
        paintDash.setColor(0xffdadada);
        paintDash.setAntiAlias(true);
        paintDash.setStrokeWidth(LUtils.dp2px(2));
        paintDash.setPathEffect(new DashPathEffect(new float[] { 6, 3 }, 10));
        Path pathDash = new Path();
        pathDash.moveTo(2, 2);
        pathDash.lineTo(getWidth() - LUtils.dp2px(36), 2);
        pathDash.lineTo(getWidth(), LUtils.dp2px(36));
        pathDash.lineTo(getWidth() - 2, getHeight() - 2);
        pathDash.lineTo(LUtils.dp2px(36), getHeight() - 2);
        pathDash.lineTo(2, getHeight() - LUtils.dp2px(36));
        pathDash.lineTo(2, 2);
        canvas.drawPath(pathDash, paintDash);

        super.draw(canvas);
    }

}
