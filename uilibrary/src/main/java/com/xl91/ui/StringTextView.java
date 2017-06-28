package com.xl91.ui;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

/**
 * 动态设置TextView某个字体颜色、大小、点击事件、下划线
 *
 * @author xiao 2017-02-18
 *         <p>
 *         <p>
 *         new StringTextView(tvTest)
 *         .setStrText("activity")
 *         .setColor(Color.GREEN)
 *         .setTextSize(1.5f)
 *         .setTargetText("BaseActivity")
 *         .setUnderline(true)
 *         .setClick(true)
 *         .setOnClickSpannableStringListener(new StringTextView.OnClickSpannableStringListener() {
 *          @Override public void onClickSpannableString(View view) { // ... }})
 *         .create();
 */
public class StringTextView {

    // 颜色
    private int color;
    // 所有文字
    private String strText;
    // 需要变色的文字
    private String targetText;
    // 是否需要下划线
    private boolean isUnderline;
    // 文字大小，这里是指原文字大小的相对倍数，如1.5是原来的大小1.5倍
    private float textSize;
    // 是否需要点击
    private boolean isClick;

    private TextView textView;

    private OnClickSpannableStringListener onClickSpannableStringListener;

    public StringTextView setOnClickSpannableStringListener(OnClickSpannableStringListener onClickSpannableStringListener) {
        this.onClickSpannableStringListener = onClickSpannableStringListener;
        return this;
    }

    public StringTextView(TextView textView) {
        this.textView = textView;
    }

    public StringTextView setColor(int color) {
        this.color = color;
        return this;
    }

    public StringTextView setStrText(String strText) {
        this.strText = strText;
        return this;
    }

    public StringTextView setTargetText(String targetText) {
        this.targetText = targetText;
        return this;
    }

    public StringTextView setUnderline(boolean underline) {
        isUnderline = underline;
        return this;
    }

    public StringTextView setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public StringTextView setClick(boolean isClick) {
        this.isClick = isClick;
        return this;
    }

    private SpannableStringBuilder start() {
        // 避免传入空字符串
        try {
            int start = strText.indexOf(targetText);
            int end = start + targetText.length();

            // 全部文字
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(strText);
            if (textSize > 0) {
                // 设置字体大小
                stringBuilder.setSpan(new RelativeSizeSpan(textSize), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (color != 0) {
                // 设置颜色
                stringBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (isUnderline) {
                stringBuilder.setSpan(new UnderlineSpan() {

                    /**
                     * 设置目标字符串的颜色和点击监听
                     * @param ds
                     */
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        // 下划线
                        ds.setUnderlineText(isUnderline);

                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            // 当可以点击时，必须设置对应的颜色
            if (isClick) {
                stringBuilder.setSpan(new ClickableSpan() {

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        if (color != 0) {
                            ds.setColor(color);
                        }

                        if (isUnderline) {
                            // 设置对应的下划线
                            boolean underlineText = ds.isUnderlineText();
                            if (!underlineText) {
                                ds.setUnderlineText(true);
                            }
                        }
                    }

                    @Override
                    public void onClick(View widget) {
                        if (null != onClickSpannableStringListener)
                            onClickSpannableStringListener.onClickSpannableString(widget);
                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            return stringBuilder;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SpannableStringBuilder();
    }

    public StringTextView create() {
        // 设置点击后的颜色为透明，否则会一直出现高亮
        textView.setHighlightColor(Color.TRANSPARENT);
        textView.setText(start());
        // 开始响应点击事件
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return this;
    }

    /**
     * 点击回调接口
     */
    public interface OnClickSpannableStringListener {
        void onClickSpannableString(View view);
    }

}
