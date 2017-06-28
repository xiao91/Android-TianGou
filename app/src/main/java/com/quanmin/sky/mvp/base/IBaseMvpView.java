package com.quanmin.sky.mvp.base;

/**
 * mvp模式
 *
 * @author xiao 2017-05-24
 */

public interface IBaseMvpView<T> {

    /**
     * 显示数据
     *
     * @param data 对应bean数据
     */
    void onShowData(T data);

    /**
     * 显示错误
     *
     * @param code 错误码
     */
    void onShowError(int code);

    /**
     * 显示进度条
     *
     */
    void onShowProgressbar();

    /**
     * 隐藏进度条
     *
     */
    void onHideProgressbar();

}
