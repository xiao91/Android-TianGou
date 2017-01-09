package com.xiao91.heiboy.mvp_m;

/**
 * 请求数据接口
 * Created by xiao91 on 2016.7.4.
 */
public interface IBaseApi<T> {

    /**
     * 请求数据
     * @param result
     */
    void requestData(T result);

}
