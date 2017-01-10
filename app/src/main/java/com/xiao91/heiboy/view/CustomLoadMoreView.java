package com.xiao91.heiboy.view;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.xiao91.heiboy.R;

/**
 * RecyclerView加载更多View
 * <p>
 * Created by xiao on 2017/1/10 0010.
 */

public class CustomLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
