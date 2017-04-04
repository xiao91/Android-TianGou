package com.xiao91.sky.mvp.view;

/**
 * mvp模式
 * Created by xiao on 2017/3/10.
 */

public interface MVPBaseView<T> {

    /**
     * 显示数据
     * @param data 对应bean数据
     */
    void onShowData(T data);

    /**
     *
     * 显示空数据
     *
     */
    void onShowEmptyData();

    /**
     * 显示网络错误
     *
     */
    void onShowNetError();

    /**
     * 显示服务器错误
     *
     */
    void onShowServerError();

}
