package com.xiao91.heiboy.impl;

import android.view.View;

/**
 * xiao
 *
 * 2017-01-07
 *
 */
public interface OnClickRecyclerChildItemListener {
    /**
     * item view 回调方法
     * @param view  被点击的view
     * @param position 索引值
     */
    void onRecyclerViewChildItemClick(View view, int position);
}
