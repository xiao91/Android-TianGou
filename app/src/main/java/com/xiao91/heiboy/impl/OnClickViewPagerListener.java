package com.xiao91.heiboy.impl;

import android.view.View;

/**
 * 点击ViewPager广告接口
 *
 * xiao
 *
 * 2017-01-07
 *
 */
public interface OnClickViewPagerListener {

    /**
     * 点击item控件
     * @param view
     * @param position
     */
    void onClickViewPagerItem(View view, int position);
}
