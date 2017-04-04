package com.xiao91.sky.http;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 用来管理retrofit2对象
 * Created by xiao on 2017/3/10.
 */

public class HttpManager {

    // 主机地址
    public static final String HOST = "";

    // 超时连接
    public static final int TIME_OUT = 10;

    // HttpService
    private HttpService mHttpService;

    // 单例模式：
    private volatile static HttpManager sHttpManager;

    private HttpManager(Context context) {
        // 创建缓存文件夹缓存网络请求
        File cacheDirectory = new File(context.getCacheDir().getAbsoluteFile(), "HttpCache");

        // 实例化一个OkHttpClient.Builder
        OkHttpClient.Builder oBuilder = new OkHttpClient.Builder();
        // 设置超时连接
        oBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        // 设置读取时间超时
        oBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        // 设置写信息超时
        oBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        // 设置缓存文件10m
        oBuilder.cache(new Cache(cacheDirectory, 10 * 1024 * 1024));
        //设置okhttp拦截器，好处是可以为你的每一个retrofit2的网络请求都增加相同的head头信息，而不用每一个请求都写头信息
        oBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                @SuppressLint("DefaultLocale") Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json; charset=UTF-8")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .header("Cache-Control", String.format("public, max-age=%d", 60))
                        .removeHeader("Pragma")
                        .build();
                return chain.proceed(request);
            }
        });


        Retrofit.Builder rBuilder = new Retrofit.Builder();
        rBuilder.client(oBuilder.build())
                // 如果网络访问返回的字符串，而不是json数据格式，要使用下面的转换器
                .addConverterFactory(ScalarsConverterFactory.create())
                // 如果网络访问返回的是json字符串，使用gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                // 此处顺序不能和上面对调，否则不能同时兼容普通字符串和Json格式字符串
                // 为了支持rxjava,需要添加下面这个把 Retrofit 转成RxJava可用的适配类
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HOST);

        // 创建HttpService
        mHttpService = rBuilder.build().create(HttpService.class);

    }

    /**
     * 使用单例模式
     *
     */
    public static HttpManager getInstance(Context context) {
        if (sHttpManager == null) {
            synchronized (HttpManager.class) {
                if (sHttpManager == null) {
                    sHttpManager = new HttpManager(context);
                }
            }
        }
        return sHttpManager;
    }

    /**
     * 如果有不同的请求HOST可继承此类并Override
     * @return HOST
     */
    protected String getHost() {
        return HOST;
    }


    /**
     * 获取HttpService实例一边访问web
     * @return mHttpService
     */
    public HttpService getHttpService() {
        return mHttpService;
    }

    /**
     * 处理http请求——常规
     *
     * @param pCall
     * @param pCallback
     */
    public void doHttpRequest(Call pCall, Callback pCallback) {
        pCall.enqueue(pCallback);
    }

    /**
     * 处理http请求——RX
     *
     * @param pObservable
     * @param pSubscriber
     */
    public void doHttpRequest(Observable pObservable, Subscriber pSubscriber) {
        Observable observable = pObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(pSubscriber);
    }

    /**
     * json方式传参
     * 将json格式的字符串转换成RequestBody
     * @param pBody
     * @return
     */
    public RequestBody getPostBody(String pBody) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), pBody);
        return body;
    }

}
