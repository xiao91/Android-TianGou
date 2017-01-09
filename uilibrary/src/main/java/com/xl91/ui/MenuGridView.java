package com.xl91.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * ScrollView嵌套GridView；GridView失去滑动特性
 * Created by xiao on 2016/8/24.
 */
public class MenuGridView extends GridView {

    public MenuGridView(Context context) {
        super(context);
    }

    public MenuGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
}
