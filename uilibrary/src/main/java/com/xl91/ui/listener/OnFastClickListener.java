package com.xl91.ui.listener;

import android.view.View;

import java.util.Calendar;

/**
 * 过快点击监听接口
 * Created by xiao on 2017/3/19.
 */

public abstract class OnFastClickListener implements View.OnClickListener{

    public static final int MIN_CLICK_DELAY_TIME = 1500;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onFastClick(v);
        }
    }

    protected abstract void onFastClick(View v);
}
