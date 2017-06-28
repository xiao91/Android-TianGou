package com.quanmin.sky.mvp.base;

import android.util.Log;

import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.builder.GetBuilder;

/**
 * mvp模式model层基类
 *
 * @author xiao 2017-05-25
 *
 */

public abstract class IBaseModel {

    // 请求成功
    protected static final int STATUS_CODE_SUCCESS = 200;

    // 打印log
    protected String tag;

    private final MyOkHttp okHttp;
    protected final Gson gson;
    protected final GetBuilder mGetBuilder;

    public IBaseModel() {
        tag = getModelTag();

        okHttp = new MyOkHttp();
        mGetBuilder = okHttp.get();
        mGetBuilder.tag(this);

        gson = new Gson();
    }

    // 获取tag
    protected abstract String getModelTag();

    /**
     * 也有可能不会调用这个析构方法，如何放到activity里面去？
     * @throws Throwable 抛出异常
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.d(tag, "调用了析构方法finalize()用于取消网络请求");
        okHttp.cancel(this);
    }
}
