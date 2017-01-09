package com.xiao91.heiboy.impl;

import android.view.View;

/**
 * item点击回调接口
 *
 * xiao
 *
 * 2017-01-07
 *
 */
public interface OnClickGridImageItemListener {

    /**
     * item view 回调方法
     * @param view  被点击的view
     * @param parentPosition 索引值
     * @param childPosition 自身索引
     */
    void onRecyclerViewItemClick(View view, int parentPosition, int childPosition);

}
