package com.xiao91.sky.http.server;

import com.xiao91.sky.app.TianGouApplication;
import com.xiao91.sky.http.HttpManager;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * 用户信息请求网络服务
 * Created by xiao on 2017/3/10.
 */

public class UserinfoServer {
    /**
     * 上传头像
     *
     * @param imgpath 图片路径
     * @param des 图片描述
     * @param subscriber 实例对象
     *
     *
     */
    public static void uploadUserIcon(String imgpath, String des, Subscriber<String> subscriber) {
        // 构建文件描述的RequestBody
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), des);
        // 根据图片路径构建图片的RequestBody
        File file = new File(imgpath);
        RequestBody imgbody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // 获取HttpManager单例，并且调用doHttpRequest方法

        // 只要调用下面这个方法就会自动返回一个
        // rxjava的Observable对象，然后这个Observable对象就会执行网络请求并返回给
        // 参数里的subscriber对象

        if (subscriber != null) {
            HttpManager.getInstance(TianGouApplication.getInstance()).getHttpService().uploadFile(description, imgbody);
        }

    }
}
