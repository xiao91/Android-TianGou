package com.xiao91.heiboy.mvp_m;

import android.util.Log;

import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;
import com.xiao91.heiboy.bean.Contents;
import com.xiao91.heiboy.bean.GoodOrBadCount;
import com.xiao91.heiboy.utils.TianGouUrls;

/**
 * 首页内容请求数据
 * Created by xiao on 2017/1/5 0005.
 */

public class ContentsModel {

    private MyOkHttp okHttp = new MyOkHttp();

    public void requestData(int type, final OnCompleteDataListener<Contents> result) {
        String url = TianGouUrls.HOST_URL + TianGouUrls.CONTENT_URL + "&type=" + type;

        okHttp.get()
                .tag(this)
                .url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {

                        Log.e("Main", "返回的数据：" + response);

                        Gson gson = new Gson();
                        Contents contents = gson.fromJson(response, Contents.class);
                        if (result != null) {
                            result.onComplete(contents);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (result != null) {
                            result.onError(error_msg);
                        }
                    }
                });
    }

    public void requestMoreData(int type, int currentCount, final OnCompleteDataListener<Contents> result) {
        String url = TianGouUrls.HOST_URL + TianGouUrls.CONTENTS_MORE_URL + "&type=" + type + "&currentCount=" + currentCount;

        okHttp.get()
                .tag(this)
                .url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {

                        Log.e("Main", "返回的数据：" + response);

                        Gson gson = new Gson();
                        Contents contents = gson.fromJson(response, Contents.class);
                        if (result != null) {
                            result.onComplete(contents);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (result != null) {
                            result.onError(error_msg);
                        }
                    }
                });
    }

    public void requestUpdateGoodCount(final String contentsId, final OnCompleteDataListener<GoodOrBadCount> result) {
        String url = TianGouUrls.HOST_URL + TianGouUrls.CONTENT_UPDATE_GOOD_COUNT + "&contentsId=" + contentsId;

        okHttp.get()
                .tag(this)
                .url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {

                        Log.e("Main", "点赞的数据：" + response);

                        Gson gson = new Gson();
                        GoodOrBadCount contents = gson.fromJson(response, GoodOrBadCount.class);
                        if (result != null) {
                            result.onComplete(contents);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (result != null) {
                            result.onError(error_msg);
                        }
                    }
                });
    }

    public void requestUpdateBadCount(final String contentsId, final OnCompleteDataListener<GoodOrBadCount> result) {
        String url = TianGouUrls.HOST_URL + TianGouUrls.CONTENT_UPDATE_BAD_COUNT + "&contentsId=" + contentsId;

        okHttp.get()
                .tag(this)
                .url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {

                        Log.e("Main", "被踩的数据：" + response);

                        Gson gson = new Gson();
                        GoodOrBadCount contents = gson.fromJson(response, GoodOrBadCount.class);
                        if (result != null) {
                            result.onComplete(contents);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (result != null) {
                            result.onError(error_msg);
                        }
                    }
                });
    }
}
