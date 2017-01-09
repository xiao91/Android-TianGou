package com.xiao91.heiboy.impl;

import android.support.v7.widget.RecyclerView;

/**
 * 监听RecyclerView滑动方向
 * Created by xiao on 2017/1/7 0007.
 */

public abstract class OnRecyclerViewScrollDownListener extends RecyclerView.OnScrollListener {

    private int mScrollThreshold;

    public abstract void onScrollUp();

    public abstract void onScrollDown();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
        if (isSignificantDelta) {
            if (dy > 0) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }
    }

    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }

}