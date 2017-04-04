package com.xiao91.sky.mvp.view;

/**
 * 泛型View接口:T是该Activity或Fragment需要填充的数据
 * Created by xiao91 on 2016.7.25
 */
public interface IBaseView<T> {

    /**
     * 显示数据
     * @param result
     */
    void showData(T result);

    /**
     * 请求数据错误时回调
     */
    void showErrorMessage(int errorCode);

}
