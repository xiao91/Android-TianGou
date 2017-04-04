package com.xiao91.sky.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

/**
 * TextView设置字体颜色
 *
 * 2017-02-18
 *
 */
public class ColorTextViewUtils {

    private int color;
    private String strText;
    private String targetText;
    private boolean isUnderline;
    private float textSize;

    private OnClickSpannableStringListener onClickSpannableStringListener;

    /**
     * @param color      资源color颜色值
     * @param strText    字符串
     * @param targetText 目标字符串
     */
    public ColorTextViewUtils(int color, String strText, String targetText) {
        this(color, strText, targetText, false);
    }

    /**
     * @param color      资源color颜色值
     * @param strText    字符串
     * @param targetText 目标字符串
     * @param isUnderline 是否下划线
     */
    public ColorTextViewUtils(int color, String strText, String targetText, boolean isUnderline) {
        this.color = color;
        this.strText = strText;
        this.targetText = targetText;
        this.isUnderline = isUnderline;
    }

    /**
     * @param color      资源color颜色值
     * @param strText    字符串
     * @param targetText 目标字符串
     * @param textSize 字体大小
     */
    public ColorTextViewUtils(int color, String strText, String targetText, float textSize) {
        this.color = color;
        this.strText = strText;
        this.targetText = targetText;
        this.textSize = textSize;
    }

    public void setOnClickSpannableStringListener(OnClickSpannableStringListener onClickSpannableStringListener) {
        this.onClickSpannableStringListener = onClickSpannableStringListener;
    }

    /**
     * 设置字体颜色和点击事件
     *
     * @return
     */
    public SpannableStringBuilder getSpannableText() {
        int start = strText.indexOf(targetText);
        int end = start + targetText.length();

        // 设置前景色
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(strText);
        stringBuilder.setSpan(new ClickableSpan() {

            /**
             * 设置目标字符串的颜色和点击监听
             * @param ds
             */
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);

                // 字体颜色
                ds.setColor(color);
                // 是否下划线
                ds.setUnderlineText(isUnderline);

            }

            /**
             * 点击回调接口
             * @param widget
             */
            @Override
            public void onClick(View widget) {
                if (onClickSpannableStringListener != null) {
                    onClickSpannableStringListener.onClickSpannableString(widget);
                }
            }

        }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return stringBuilder;
    }

    /**
     * 设置字体颜色和大小
     * @return
     */
    public SpannableStringBuilder getSpannableColorAndSize() {
        int start = strText.indexOf(targetText);
        int end = start + targetText.length();

        // 设置前景色
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(strText);
        // 设置字体大小
        stringBuilder.setSpan(new RelativeSizeSpan(textSize), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置颜色
        stringBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return stringBuilder;
    }

    /**
     * 点击回调接口
     */
    public interface OnClickSpannableStringListener {
        void onClickSpannableString(View view);
    }
}
