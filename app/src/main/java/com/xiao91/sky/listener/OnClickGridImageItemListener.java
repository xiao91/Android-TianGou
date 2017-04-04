package com.xiao91.sky.listener;

import android.view.View;

import java.util.ArrayList;

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
     * @param position 当前位置
     * @param urls 当前数据
     *
     */
    void onClickGridImageItemListener(View view, int position, ArrayList<String> urls);

}
