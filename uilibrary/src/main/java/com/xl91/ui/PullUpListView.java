package com.xl91.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 上拉加载的ListView
 * Created by Administrator on 2016.7.7.
 */
public class PullUpListView extends ListView implements AbsListView.OnScrollListener {

    // 底部视图
    private View footerView;

    // 定义底部视图是否正处于加载更多状态
    private boolean isLoadingMore = false;

    // 定义底部视图高度
    private int footerViewHeight;

    private Context context;

    /**
     * 自定义一个接口,监听数据请求
     *
     */
    private OnLoadingMoreListener mOnLoadingMoreListener;

    public PullUpListView(Context context) {
        super(context);
        this.context = context;
        initListView();
    }

    public PullUpListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initListView();
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        // 为ListView设置滑动监听
        setOnScrollListener(this);

        // 去掉底部分割线
        setFooterDividersEnabled(false);

        initFooterView();
    }

    /**
     * 定义底部视图
     *
     */
    private void initFooterView() {
        footerView = LayoutInflater.from(context).inflate(R.layout.loading_more_view, null);

        // 测量视图
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        // 隐藏视图
        footerView.setPadding(0, -footerViewHeight, 0, 0);

        // 添加底部视图
        addFooterView(footerView);
    }

    /**
     * 完成刷新，重置状态
     * 在UI线程中调用此方法
     */
    public void completedLoadMore () {
        if (isLoadingMore) {
            // 隐藏
            footerView.setPadding(0, -footerViewHeight, 0, 0);
            // 结束之后置为false
            isLoadingMore = false;
        }
    }

    /**
     * 设置接口
     * @param mOnLoadingMoreListener
     */
    public void setLoadMoreListener(OnLoadingMoreListener mOnLoadingMoreListener) {
        this.mOnLoadingMoreListener = mOnLoadingMoreListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        // 判断是否停止了滑动，是否滑动到最后一个条目
        if (i == OnScrollListener.SCROLL_STATE_IDLE && getLastVisiblePosition() == (getCount() - 1)
                && !isLoadingMore) {
            isLoadingMore = true;
            // 显示底部视图
            footerView.setPadding(0, 0, 0, 0);
            // 将对应的item放到最顶端
            setSelection(getCount());

            /**
             * 接口回调方法
             *
             */
            if (mOnLoadingMoreListener != null) {
                mOnLoadingMoreListener.onLoadingMore();
            }
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * 接口
     */
    public interface OnLoadingMoreListener {
        void onLoadingMore();
    }
}
