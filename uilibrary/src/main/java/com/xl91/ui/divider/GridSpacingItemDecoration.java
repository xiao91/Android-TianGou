package com.xl91.ui.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView的GridLayoutManager布局时，间隔item，没有分隔线
 * Created by Administrator on 2016.6.8.
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    // 行数
    private int spanCount;
    // item间距
    private int spacing;
    // 是否留边界
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;

            // 忽略上下间隔
//            if (position < spanCount) {
//                outRect.top = spacing;
//            }
//            outRect.bottom = spacing;
        } else {
            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;
//            if (position >= spanCount) {
//                outRect.top = spacing;
//            }
        }
    }
}
