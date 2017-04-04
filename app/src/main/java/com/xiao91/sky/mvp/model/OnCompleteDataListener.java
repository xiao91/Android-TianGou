package com.xiao91.sky.mvp.model;

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
     * 数据加载错误，500：服务器错误，-1：网络错误
     */
    void onError(int errorCode);
}
