package com.miguan.yjy.module.article;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.text.DynamicLayout;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */

public abstract class BaseEvaluateViewHolder extends BaseViewHolder<Evaluate> {

    private static final int MAX_LINE_COUNT = 6;

    private static final String EXPAND_PLACEHOLDER = "... im";

    private static final String ELLIPSIS_HINT = "... ";

    @BindView(R.id.tv_evaluate_content)
    TextView mTvContent;

    public BaseEvaluateViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Evaluate data) {
        Layout layout = new DynamicLayout(data.getComment(), mTvContent.getPaint(), getContentTextWidth(),
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        int lineCount = layout.getLineCount();
        if (lineCount > MAX_LINE_COUNT) {
            int indexEnd = layout.getLineEnd(MAX_LINE_COUNT - 1);
            int indexEndTrimmed = indexEnd - EXPAND_PLACEHOLDER.replace(ELLIPSIS_HINT, "").length();

            float tailWidth = mTvContent.getPaint().measureText(data.getComment().subSequence(indexEndTrimmed - 1, indexEnd).toString());
            float widthTailReplaced = mTvContent.getPaint().measureText(EXPAND_PLACEHOLDER);
            if (tailWidth < widthTailReplaced) {
                int extraOffset = 0;
                int extraWidth = 0;
                while (tailWidth + extraWidth < widthTailReplaced) {
                    extraOffset--;
                    if (indexEnd + extraOffset <= data.getComment().length()) {
                        extraWidth = (int) mTvContent.getPaint().measureText(
                                data.getComment().subSequence(indexEndTrimmed + extraOffset, indexEndTrimmed).toString());
                    } else {
                        break;
                    }
                }
                indexEndTrimmed += extraOffset;
            }

            String fixText = removeEndLineBreak(data.getComment().subSequence(0, indexEndTrimmed));

            VerticalImageSpan span = new VerticalImageSpan(getContext(), R.mipmap.ic_product_detail_record_down);
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(fixText);
            stringBuilder.append(EXPAND_PLACEHOLDER);
            stringBuilder.setSpan(span, (fixText + "... ").length(), stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            mTvContent.setText(stringBuilder);
        } else {
            mTvContent.setText(Html.fromHtml(data.getComment()));
        }
        itemView.setOnClickListener(v -> EvaluateDetailPresenter.start(getContext(), data.getId()));
    }

    private String removeEndLineBreak(CharSequence text) {
        String str = text.toString();
        while (str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    protected float getDimen(int id) {
        return getContext().getResources().getDimension(id);
    }

    public abstract int getContentTextWidth();

    private class VerticalImageSpan extends ImageSpan {

        public VerticalImageSpan(Context context, @DrawableRes int resourceId) {
            super(context, resourceId);
        }

        public VerticalImageSpan(Drawable d, int verticalAlignment) {
            super(d, verticalAlignment);
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            Drawable drawable = getDrawable();
            Rect rect = drawable.getBounds();
            if (fm != null) {
                Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                int fontHeight = fmPaint.bottom - fmPaint.top;
                int drHeight = rect.bottom - rect.top;

                int top = drHeight / 2 - fontHeight / 4;
                int bottom = drHeight / 2 + fontHeight / 4;

                fm.ascent = -bottom;
                fm.top = -bottom;
                fm.bottom = top;
                fm.descent = top;
            }
            return rect.right;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            Drawable drawable = getDrawable();
            canvas.save();
            int transY = 0;
            transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
            canvas.translate(x, transY);
            drawable.draw(canvas);
            canvas.restore();
        }
    }

}
