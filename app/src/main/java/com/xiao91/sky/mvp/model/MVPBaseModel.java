package com.xiao91.sky.mvp.model;

/**
 * 请求数据接口
 * Created by xiao91 on 2016.7.4.
 */
public interface MVPBaseModel<T> {

    /**
     * 请求数据
     * @param result
     */
    void requestData(T result);

}
