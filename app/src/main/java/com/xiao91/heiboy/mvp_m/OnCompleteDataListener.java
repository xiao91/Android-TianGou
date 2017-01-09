package com.xiao91.heiboy.mvp_m;

/**
 * 数据加载监听
 * Created by xiao91 on 2016.07.25.
 */
public interface OnCompleteDataListener<T> {

    /**
     * 数据加载完成
     * @param result
     */
    void onComplete(T result);

    /**
     * 数据加载错误
     */
    void onError(String errorMsg);
}
