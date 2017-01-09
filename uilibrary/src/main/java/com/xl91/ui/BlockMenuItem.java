package com.xl91.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 左右菜单设置item组件
 *
 */
public class BlockMenuItem extends View {

//    BlockIconTextLayout

    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_BORDER_COLOR = Color.GRAY;
    private static final int DEFAULT_TEXT_SIZE = 16;

    private static final int DEFAULT_EXTEND_TEXT_COLOR = Color.GRAY;
    private static final int DEFAULT_EXTEND_TEXT_SIZE = 16;
    private float mTopBorder ;
    private float mBottomBorder;
    private int   mBorderColor;
    private int   mTextColor;
    private int   mExtendTextColor;
    private float mIconMargin;
    private float mExtendIconMargin;
    private float mTextMargin;
    private float mExtendTextMargin;
    private boolean mTopBorderStartFromText;
    private boolean mBottomBorderStartFromText;
    private boolean mVertical;
    private int mHeight;
    private int mWidth;
    private float mTextSize;
    private float mExtendTextSize;
    private float mMainIconSize;
    private float mExtendIconSize;
    private Bitmap mIcon;
    private String mText;
    private String mExtendText;
    private Bitmap mExtendIcon;
    private Paint mBorderPaint;
    private Paint mTextPaint;

    public BlockMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr( context,  attrs);
    }

    public BlockMenuItem(Context context, AttributeSet attrs, int defStyleRes) {
        super(context, attrs, defStyleRes);
        initAttr(context, attrs);
        mWidth = getWidth();
        mHeight = getHeight();

    }
    private void initAttr(Context context,AttributeSet attrs){
        mBorderPaint = new Paint();
        mTextPaint = new Paint();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BlockMenuItem);
        mVertical = ta.getBoolean(R.styleable.BlockMenuItem_vertical, false);
        mTopBorder = ta.getDimension(R.styleable.BlockMenuItem_topBorder, 0);
        mBottomBorder = ta.getDimension(R.styleable.BlockMenuItem_bottomBorder, 0);
        mBorderColor = ta.getColor(R.styleable.BlockMenuItem_borderColor, DEFAULT_BORDER_COLOR);
        mTextColor = ta.getColor(R.styleable.BlockMenuItem_textColor, DEFAULT_TEXT_COLOR);
        mExtendTextColor = ta.getColor(R.styleable.BlockMenuItem_extendTextColor, DEFAULT_EXTEND_TEXT_COLOR);
        mTopBorderStartFromText = ta.getBoolean(R.styleable.BlockMenuItem_topBorderStartFromText, false);
        mBottomBorderStartFromText = ta.getBoolean(R.styleable.BlockMenuItem_bottomBorderStartFromText, false);
        mIconMargin = ta.getDimension(R.styleable.BlockMenuItem_iconMargin, 0);
        mExtendIconMargin = ta.getDimension(R.styleable.BlockMenuItem_extendIconMargin, 0);
        mTextSize = ta.getDimension(R.styleable.BlockMenuItem_textSize, DEFAULT_TEXT_SIZE);
        mExtendTextSize = ta.getDimension(R.styleable.BlockMenuItem_extendTextSize, DEFAULT_EXTEND_TEXT_SIZE);
        mTextMargin = ta.getDimension(R.styleable.BlockMenuItem_textMargin, 0);
        mExtendTextMargin = ta.getDimension(R.styleable.BlockMenuItem_extendTextMargin, 0);
        mMainIconSize = ta.getDimensionPixelSize(R.styleable.BlockMenuItem_mainIconSize, 0);
        mExtendIconSize = ta.getDimensionPixelSize(R.styleable.BlockMenuItem_extendIconSize, 0);
        mText = ta.getString(R.styleable.BlockMenuItem_text);
        mExtendText =ta.getString(R.styleable.BlockMenuItem_extendText);
        BitmapDrawable lBitmapDrawable = (BitmapDrawable) ta.getDrawable(R.styleable.BlockMenuItem_mainIcon);
        if (lBitmapDrawable != null){
            mIcon = lBitmapDrawable.getBitmap();
        }
        BitmapDrawable rBitmapDrawable = (BitmapDrawable) ta.getDrawable(R.styleable.BlockMenuItem_extendIcon);
        if (rBitmapDrawable != null){
            mExtendIcon = rBitmapDrawable.getBitmap();
        }


        mBorderPaint.setStyle(Paint.Style.STROKE);//设置非填充
        mBorderPaint.setAntiAlias(true);//锯齿不显示
        mBorderPaint.setColor(mBorderColor);

        mTextPaint.setAntiAlias(true);
        mTextPaint.setFilterBitmap(true);
        ta.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mVertical){
            showVertical(canvas);
        } else {
            showHorizontal(canvas);
        }



    }

    private void showVertical(Canvas canvas) {
        if (mIcon != null){
            Rect src = new Rect();
            src.set(0, 0, mIcon.getWidth(), mIcon.getHeight());
            mMainIconSize = mMainIconSize == 0?src.width():mMainIconSize;
            float scale = mMainIconSize/new Float(src.width());
            Rect dest = new Rect();
            int startX = mWidth/2- (int)(src.width()*scale)/2;
            int startY = (int)mIconMargin;
            dest.left = startX;
            dest.top = startY;
            dest.right = dest.left + (int)(scale*src.width());
            dest.bottom = dest.top + (int)(scale*src.height());
            canvas.drawBitmap(mIcon, src, dest, mTextPaint);

        }



        if (mText != null) {
            Rect rect = new Rect();
            mTextPaint.setTextSize(mTextSize);
            mTextPaint.getTextBounds(mText, 0, mText.length(), rect);
            canvas.drawText(mText, mWidth / 2 - rect.width() / 2, mHeight - mTextMargin - rect.height() / 2, mTextPaint);
        }


    }

    private void showHorizontal(Canvas canvas) {
        float tempTextMargin = mTextMargin;
        float tempExtendTextMargin = mExtendTextMargin;
        if (mIcon != null){
            Rect src = new Rect();
            src.set(0, 0, mIcon.getWidth(), mIcon.getHeight());
            mMainIconSize = mMainIconSize == 0?src.width():mMainIconSize;
            float scale = mMainIconSize/new Float(src.width());
            Rect dest = new Rect();
            int startX = (int)mIconMargin;
            int startY = (int)(mHeight/2- src.height()*scale/2);
            dest.left = startX;
            dest.top = startY;
            dest.right = dest.left + (int)(scale*src.width());
            dest.bottom = dest.top + (int)(scale*src.height());
            canvas.drawBitmap(mIcon,src,dest,mTextPaint);
            tempTextMargin += src.width() + mIconMargin;
        }

        if (mExtendIcon != null){
            Rect src = new Rect();
            src.set(0, 0, mExtendIcon.getWidth(), mExtendIcon.getHeight());
            mExtendIconSize = mExtendIconSize == 0?src.width():mExtendIconSize;
            Rect dest = new Rect();
            int startX = (int)(mWidth- mExtendIcon.getWidth()-mExtendIconMargin);
            int startY = (int)(mHeight/2- src.height()/2);
            float scale = mExtendIconSize/new Float(src.width());
            dest.left = startX;
            dest.top = startY;
            dest.right = dest.left + (int)(scale*src.width());
            dest.bottom = dest.top + (int)(scale*src.height());
            canvas.drawBitmap(mExtendIcon,src,dest,mTextPaint);

            tempExtendTextMargin += src.width()+mExtendIconMargin;

        }

        if (mText != null) {
            mTextPaint.setColor(mTextColor);
            mTextPaint.setTextSize(mTextSize);
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            int baseline =  (fontMetrics.top + fontMetrics.bottom  ) / 2 ; //FontMetrics.top,bottom的数值是个负数
            canvas.drawText(mText, tempTextMargin, mHeight / 2 -baseline, mTextPaint);
        }
        if(mExtendText !=null){
            mTextPaint.setColor(mExtendTextColor);
            mTextPaint.setTextSize(mExtendTextSize);
            tempExtendTextMargin += mTextPaint.measureText(mExtendText);
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            int baseline =  (fontMetrics.top + fontMetrics.bottom  ) / 2 ;
            canvas.drawText(mExtendText, mWidth-tempExtendTextMargin, mHeight / 2 -baseline, mTextPaint);
        }

        if (mTopBorder > 0){
            mBorderPaint.setStrokeWidth(mTopBorder);
            canvas.drawLine(mTopBorderStartFromText ? tempTextMargin : 0, mTopBorder / 2, mWidth, mTopBorder / 2, mBorderPaint);

        }

        if (mBottomBorder > 0){
            mBorderPaint.setStrokeWidth(mBottomBorder);
            canvas.drawLine(mBottomBorderStartFromText ? tempTextMargin : 0, mHeight - mBottomBorder / 2, mWidth, mHeight - mBottomBorder / 2, mBorderPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    /**
     * 设置文字
     * @param text
     */
    public void setText(String text){
        mText=text;
        invalidate();
    }

    /**
     * 设置右边的文字(当且仅当横向布局时生效)
     * @param text
     */
    public void setExtendText(String text){
        if(mVertical)return;

        mExtendText=text;
        invalidate();
    }
}