package com.quanmin.sky.mvp.model;

import android.text.TextUtils;
import android.util.Log;

import com.quanmin.sky.bean.ContentEntry;
import com.quanmin.sky.config.ConfigUrl;
import com.quanmin.sky.mvp.base.IBaseModel;
import com.quanmin.sky.mvp.base.OnCompleteDataListener;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 首页内容请求数据
 * Created by xiao on 2017/1/5 0005.
 */

public class ContentsModel extends IBaseModel {

    @Override
    protected String getModelTag() {
        return ContentsModel.class.getSimpleName();
    }

    /**
     * 请求数据
     * @param type 类型
     * @param listener 接口监听
     */
    public void requestData(int type, final OnCompleteDataListener<ContentEntry> listener) {
        String url = ConfigUrl.CONTENT_ITEM + "&content_type=" + type;
        mGetBuilder.url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e(tag, "onSuccess()：response=" + response);

                        if (statusCode == STATUS_CODE_SUCCESS) {
                            ContentEntry contentEntry = gson.fromJson(response, ContentEntry.class);

                            if (listener != null) {
                                listener.onComplete(contentEntry);
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e(tag, "onFailure()：statusCode=" + statusCode);

                        if (null != listener) {
                            listener.onError(statusCode);
                        }

                    }
                });
    }

    /**
     * 请求更多数据
     * @param contentType 类型
     * @param currentCount 当前数量
     * @param listener 接口监听
     */
    public void requestMoreData(int contentType, int currentCount, final OnCompleteDataListener<ContentEntry> listener) {
        String url = ConfigUrl.CONTENT_MORE + "&content_type=" + contentType + "&current_count=" + currentCount;
        mGetBuilder.url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.d(tag, "onSuccess()：response=" + response);

                        if (statusCode == STATUS_CODE_SUCCESS) {
                            ContentEntry contentEntry = gson.fromJson(response, ContentEntry.class);

                            if (listener != null) {
                                listener.onComplete(contentEntry);
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e(tag, "onFailure()：statusCode=" + statusCode);

                        if (null != listener) {
                            listener.onError(statusCode);
                        }

                    }
                });
    }

    public void requestUpdateGoodCount(String countId, String userId, String deviceId, final OnCompleteDataListener<Integer> listener) {
        StringBuilder builder = new StringBuilder();
        builder.append(ConfigUrl.CONTENT_UPDATE_GOOD_COUNT);
        builder.append("&count_id=");
        builder.append(countId);
        if (!TextUtils.isEmpty(userId)) {
            builder.append("&user_id=");
            builder.append(userId);
        }else {
            // 如果未登录就添加设备号
            if (!TextUtils.isEmpty(deviceId)) {
                builder.append("&device_code=");
                builder.append(deviceId);
            }
        }

        mGetBuilder.url(builder.toString())
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        if (statusCode == STATUS_CODE_SUCCESS) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject data = jsonObject.getJSONObject("data");
                                int code = data.getInt("code");
                                if (null != listener) {
                                    listener.onComplete(code);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e(tag, "onFailure()：statusCode=" + statusCode);

                        if (null != listener) {
                            listener.onError(statusCode);
                        }

                    }
                });
    }

    public void requestUpdateBadCount(String countId, String userId, String deviceId, final OnCompleteDataListener<Integer> listener) {
        StringBuilder builder = new StringBuilder();
        builder.append(ConfigUrl.CONTENT_UPDATE_BAD_COUNT);
        builder.append("&count_id=");
        builder.append(countId);
        if (!TextUtils.isEmpty(userId)) {
            builder.append("&user_id=");
            builder.append(userId);
        }else {
            // 如果未登录就添加设备号
            if (!TextUtils.isEmpty(deviceId)) {
                builder.append("&device_code=");
                builder.append(deviceId);
            }
        }

        mGetBuilder.url(builder.toString())
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.d(tag, "onSuccess()：response=" + response);

                        if (statusCode == STATUS_CODE_SUCCESS) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject data = jsonObject.getJSONObject("data");
                                int code = data.getInt("code");
                                if (null != listener) {
                                    listener.onComplete(code);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(tag, "onFailure()：statusCode=" + statusCode);

                        if (null != listener) {
                            listener.onError(statusCode);
                        }

                    }
                });
    }

}
