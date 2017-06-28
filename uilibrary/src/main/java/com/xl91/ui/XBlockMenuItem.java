package com.xl91.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 左右设置菜单
 * Created by xl on 2017/2/19 0019.
 */

public class XBlockMenuItem extends LinearLayout {

    private static final int DEFAULT_MAIN_TEXT_COLOR = Color.parseColor("#565656");
    private static final int DEFAULT_MAIN_TEXT_SIZE = 16;
    private static final int DEFAULT_MAIN_TEXT_PADDING = 16;
    private static final int DEFAULT_MAIN_TEXT_DRAWABLE_PADDING = 16;

    private static final int DEFAULT_EXTEND_TEXT_COLOR = Color.GRAY;
    private static final int DEFAULT_EXTEND_TEXT_SIZE = 12;
    private static final int DEFAULT_EXTEND_TEXT_MARGIN_NAVIGATION = 10;

    private static final int DEFAULT_RIGHT_NAVIGATION_PADDING = 10;

    private static final float DEFAULT_TOP_BOTTOM_BORDER_SIZE = 0.5f;
    private static final int DEFAULT_TOP_BOTTOM_BORDER_COLOR = Color.parseColor("#DDDDDD");

    private String mainText;
    private String extendText;

    private TextView tvMain;
    private TextView tvExtend;
    private ImageView ivRightNavigation;

    public XBlockMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOrientation(VERTICAL);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.bmi_ll, this);

        tvMain = (TextView) findViewById(R.id.tv_main);
        tvExtend = (TextView) findViewById(R.id.tv_extend);
        ivRightNavigation = (ImageView) findViewById(R.id.iv_right_navigation);

        View top_line = findViewById(R.id.top_border);
        View bottom_line = findViewById(R.id.bottom_border);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XBlockMenuItem, 0, 0);

        Drawable drawable = typedArray.getDrawable(R.styleable.XBlockMenuItem_mainDrawable);
        int mainTextDrawablePadding = (int) typedArray.getDimension(R.styleable.XBlockMenuItem_mainTextDrawablePadding, 0);
        if (mainTextDrawablePadding == 0) {
            tvMain.setCompoundDrawablePadding(dp2Px(DEFAULT_MAIN_TEXT_DRAWABLE_PADDING));
        }else {
            tvMain.setCompoundDrawablePadding(mainTextDrawablePadding);
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvMain.setCompoundDrawables(drawable, null, null, null);
        }

        mainText = typedArray.getString(R.styleable.XBlockMenuItem_mainText);
        int mainTextSize = (int) typedArray.getDimension(R.styleable.XBlockMenuItem_mainTextSize, 0);
        int mainTextColor = typedArray.getColor(R.styleable.XBlockMenuItem_mainTextColor, DEFAULT_MAIN_TEXT_COLOR);
        int mainTextPadding = (int) typedArray.getDimension(R.styleable.XBlockMenuItem_mainTextPadding, 0);
        tvMain.setText(mainText);
        if (mainTextSize == 0) {
            tvMain.setTextSize(DEFAULT_MAIN_TEXT_SIZE);
        }else {
            tvMain.setTextSize(px2sp(mainTextSize));
        }
        tvMain.setTextColor(mainTextColor);
        if (mainTextPadding == 0) {
            int m = dp2Px(DEFAULT_MAIN_TEXT_PADDING);
            tvMain.setPadding(m, m, m, m);
        }else {
            tvMain.setPadding(mainTextPadding, mainTextPadding, mainTextPadding, mainTextPadding);
        }

        extendText = typedArray.getString(R.styleable.XBlockMenuItem_extendText);
        int extendTextSize = (int) typedArray.getDimension(R.styleable.XBlockMenuItem_extendTextSize, 0);
        int extendTextColor = typedArray.getColor(R.styleable.XBlockMenuItem_extendTextColor, DEFAULT_EXTEND_TEXT_COLOR);
        tvExtend.setText(extendText);
        if (extendTextSize == 0) {
            tvExtend.setTextSize(DEFAULT_EXTEND_TEXT_SIZE);
        }else {
            tvExtend.setTextSize(px2sp(extendTextSize));
        }
        tvExtend.setTextColor(extendTextColor);

        // 右边TextView距离导航图片距离
        int extendTextMarginNavigationDrawable = (int) typedArray.getDimension(R.styleable.XBlockMenuItem_extendTextMarginNavigationDrawable, DEFAULT_EXTEND_TEXT_MARGIN_NAVIGATION);
        int rightNavigationDrawablePadding = (int) typedArray.getDimension(R.styleable.XBlockMenuItem_rightNavigationDrawablePadding, 0);
        if (rightNavigationDrawablePadding == 0) {
            int m = dp2Px(DEFAULT_RIGHT_NAVIGATION_PADDING);
            ivRightNavigation.setPadding(0, 0, m, 0);
        }else {
            ivRightNavigation.setPadding(0, 0, rightNavigationDrawablePadding, 0);
        }
        Drawable rightNavigationDrawable = typedArray.getDrawable(R.styleable.XBlockMenuItem_rightNavigationDrawable);
        if (rightNavigationDrawable != null) {
            ivRightNavigation.setImageDrawable(rightNavigationDrawable);

            int paddingRight = ivRightNavigation.getPaddingRight();
            int width = rightNavigationDrawable.getIntrinsicWidth();
            tvExtend.setPadding(0, 0, paddingRight + width + extendTextMarginNavigationDrawable, 0);
        }else {
            ivRightNavigation.setVisibility(GONE);
        }

        int topAndBottomBorderColor = typedArray.getColor(R.styleable.XBlockMenuItem_topAndBottomBorderColor, DEFAULT_TOP_BOTTOM_BORDER_COLOR);
        top_line.setBackgroundColor(topAndBottomBorderColor);
        bottom_line.setBackgroundColor(topAndBottomBorderColor);

        float topAndBottomBorderSize = typedArray.getDimension(R.styleable.XBlockMenuItem_topAndBottomBorderSize, 0);
        if (topAndBottomBorderSize == 0) {
            int size = dp2Px(DEFAULT_TOP_BOTTOM_BORDER_SIZE);
            top_line.setMinimumHeight(size);
            bottom_line.setMinimumHeight(size);
        }else {
            top_line.setMinimumHeight((int) topAndBottomBorderSize);
            bottom_line.setMinimumHeight((int) topAndBottomBorderSize);
        }

        boolean topBorderStartFromText = typedArray.getBoolean(R.styleable.XBlockMenuItem_topBorderStartFromText, false);
        boolean showTopBorder = typedArray.getBoolean(R.styleable.XBlockMenuItem_showTopBorder, false);
        if (showTopBorder) {
            top_line.setVisibility(VISIBLE);
        }else {
            top_line.setVisibility(GONE);
        }

        if (topBorderStartFromText) {
            int totalPaddingLeft = tvMain.getTotalPaddingLeft();
            LayoutParams lp = new LayoutParams(bottom_line.getLayoutParams());
            lp.setMargins(totalPaddingLeft, 0, 0, 0);
            top_line.setLayoutParams(lp);
        }

        boolean bottomBorderStartFromText = typedArray.getBoolean(R.styleable.XBlockMenuItem_bottomBorderStartFromText, false);
        boolean showBottomBorder = typedArray.getBoolean(R.styleable.XBlockMenuItem_showBottomBorder, false);
        if (showBottomBorder) {
            bottom_line.setVisibility(VISIBLE);
        }else {
            bottom_line.setVisibility(GONE);
        }
        if (bottomBorderStartFromText) {
            int totalPaddingLeft = tvMain.getTotalPaddingLeft();
            LayoutParams lp = new LayoutParams(bottom_line.getLayoutParams());
            lp.setMargins(totalPaddingLeft, 0, 0, 0);
            bottom_line.setLayoutParams(lp);
        }

        typedArray.recycle();
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public void setextendText(String extendText) {
        tvExtend.setText(extendText);
    }

    public TextView getMainTextView() {
        return tvMain;
    }

    public TextView getExtendTextView() {
        return tvExtend;
    }

    public ImageView getIvRightNavigation() {
        return ivRightNavigation;
    }

    private int dp2Px(float dpValue) {
        if (dpValue == 0) return 0;

        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *
     * @return
     */
    public int px2sp(float pxValue) {
        if (pxValue == 0) return 0;

        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
