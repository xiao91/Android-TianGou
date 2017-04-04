package com.xiao91.sky.mvp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;
import com.xiao91.sky.app.TianGouApplication;
import com.xiao91.sky.bean.Contents;
import com.xiao91.sky.bean.GoodOrBadCount;
import com.xiao91.sky.manager.NetworkManager;
import com.xiao91.sky.utils.TGUrls;

/**
 * 首页内容请求数据
 * Created by xiao on 2017/1/5 0005.
 */

public class ContentsModel {

    private MyOkHttp okHttp = new MyOkHttp();

    /**
     * @param type
     * @param result
     */
    public void requestData(int type, final OnCompleteDataListener<Contents> result) {
        String url = TGUrls.CONTENT_ITEM + "&type=" + type;

        okHttp.get()
                .tag(this)
                .url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("content", "onSuccess:statusCode=" + statusCode);
                        Log.e("Main", "返回的数据：" + response);
                        if (statusCode == 200) {
                            Gson gson = new Gson();
                            Contents contents = gson.fromJson(response, Contents.class);
                            if (contents.ret == 200) {
                                if (result != null) {
                                    result.onComplete(contents);
                                }
                            } else {
                                // 服务器错误
                                if (result != null) {
                                    result.onError(500);
                                }
                            }
                        } else {
                            // 服务器错误
                            if (result != null) {
                                result.onError(500);
                            }
                        }
                    }

                    /**
                     * 请求失败会返回0，一般是没有网络或服务器没有打开
                     * @param statusCode
                     * @param error_msg
                     */
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e("content", "onFailure:statusCode=" + statusCode);
                        NetworkManager manager = new NetworkManager();
                        boolean networkConnected = manager.isNetworkConnected(TianGouApplication.getInstance());
                        if (networkConnected) {
                            // 服务器错误
                            if (result != null) {
                                result.onError(500);
                            }
                        }else {
                            // 网络错误
                            if (result != null) {
                                result.onError(-1);
                            }
                        }
                    }
                });

    }

    public void requestMoreData(int type, int currentCount, final OnCompleteDataListener<Contents> result) {
        String url = TGUrls.CONTENT_MORE + "&type=" + type + "&current_count=" + currentCount;

        okHttp.get()
                .tag(this)
                .url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {

                        Log.e("Main", "返回的数据：" + response);

                        if (statusCode== 200) {
                            Gson gson = new Gson();
                            Contents contents = gson.fromJson(response, Contents.class);

                            if (contents.ret == 200) {
                                if (result != null) {
                                    result.onComplete(contents);
                                }
                            } else {
                                // 服务器错误
                                if (result != null) {
                                    result.onError(500);
                                }
                            }
                        }else {
                            // 服务器错误
                            if (result != null) {
                                result.onError(500);
                            }
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        NetworkManager manager = new NetworkManager();
                        boolean networkConnected = manager.isNetworkConnected(TianGouApplication.getInstance());
                        if (networkConnected) {
                            // 服务器错误
                            if (result != null) {
                                result.onError(500);
                            }
                        }else {
                            // 网络错误
                            if (result != null) {
                                result.onError(-1);
                            }
                        }
                    }
                });
    }

    public void requestUpdateGoodCount(final String contentsId, final OnCompleteDataListener<GoodOrBadCount> result) {
        String url = TGUrls.CONTENT_UPDATE_GOOD_COUNT + "&content_id=" + contentsId;

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
                            result.onError(-1);
                        }
                    }
                });
    }

    public void requestUpdateBadCount(final String contentsId, final OnCompleteDataListener<GoodOrBadCount> result) {
        String url = TGUrls.CONTENT_UPDATE_BAD_COUNT + "&content_id=" + contentsId;

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
                        // 服务器错误
                        if (statusCode >= 400) {
                            if (result != null) {
                                result.onError(500);
                            }
                        } else {
                            // 网络错误
                            if (result != null) {
                                result.onError(-1);
                            }
                        }

                    }
                });
    }
}
