package com.quanmin.sky.mvp.base;

/**
 * 数据加载监听
 *
 * @author xiao 2017-05-24
 */
public interface OnCompleteDataListener<T> {

    /**
     * 数据加载完成
     *
     * @param data 数据
     */
    void onComplete(T data);

    /**
     * 请求错误
     * @param code 错误码，500服务器出错，0网络或服务器关闭
     */
    void onError(int code);
}
