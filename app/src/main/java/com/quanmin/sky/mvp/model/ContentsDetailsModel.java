package com.quanmin.sky.mvp.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.quanmin.sky.bean.AddCommentEntry;
import com.quanmin.sky.bean.FocusEntry;
import com.quanmin.sky.bean.CommentEntry;
import com.quanmin.sky.config.ConfigUrl;
import com.quanmin.sky.mvp.base.IBaseModel;
import com.quanmin.sky.mvp.base.OnCompleteDataListener;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 内容详情：主要请求评论相关的数据
 * Created by xiao on 2017/1/12 0012.
 */

public class ContentsDetailsModel extends IBaseModel {

    @Override
    protected String getModelTag() {
        return ContentsDetailsModel.class.getSimpleName();
    }

    /**
     * 请求评论数据
     *
     * @param userId     用户id
     * @param contentsId 该内容id
     * @param listener
     */
    public void requestComments(String userId, String contentsId, final OnCompleteDataListener<CommentEntry> listener) {
        String url = ConfigUrl.COMMENT_HOT_NEW + "&content_id=" + contentsId + "&user_id=" + userId;
        Log.e(tag, "评论：url=" + url);

        mGetBuilder.url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e(tag, "所有评论的数据：" + response);

                        if (statusCode == STATUS_CODE_SUCCESS) {
                            Gson gson = new Gson();
                            CommentEntry contentsDetail = gson.fromJson(response, CommentEntry.class);
                            if (listener != null) {
                                listener.onComplete(contentsDetail);
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
     * 更多数据
     *
     * @param contentsId
     * @param createTime
     * @param listener
     */
    public void requestMoreComments(String contentsId, String createTime, final OnCompleteDataListener<CommentEntry> listener) {
        String url = ConfigUrl.COMMENT_HOT_NEW + "&content_id=" + contentsId + "&create_time" + createTime;

        mGetBuilder.url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        if (statusCode == STATUS_CODE_SUCCESS) {
                            Gson gson = new Gson();
                            CommentEntry comments = gson.fromJson(response, CommentEntry.class);
                            if (listener != null) {
                                listener.onComplete(comments);
                            }

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (null != listener) {
                            listener.onError(statusCode);
                        }
                    }
                });
    }

    /**
     * 发表评论
     *
     * @param contentsId
     * @param content
     * @param from_uid
     * @param listener
     */
    public void requestAddComment(String contentsId, String content, String from_uid, String to_uid, final OnCompleteDataListener<AddCommentEntry> listener) {
        StringBuilder builder = new StringBuilder();
        builder.append(ConfigUrl.COMMENT_ADD);
        builder.append("&content_id=");
        builder.append(contentsId);
        builder.append("&comment_detail=");
        builder.append(content);
        builder.append("&from_uid=");
        builder.append(from_uid);
        if (!TextUtils.isEmpty(to_uid)) {
            builder.append("&to_uid=");
            builder.append(to_uid);
        }

        Log.d(tag, "url=" + builder.toString());

        mGetBuilder.url(builder.toString())
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        if (statusCode == STATUS_CODE_SUCCESS) {
                            Gson gson = new Gson();
                            AddCommentEntry addCommentEntry = gson.fromJson(response, AddCommentEntry.class);
                            if (listener != null) {
                                listener.onComplete(addCommentEntry);
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

    /**
     * 获取关注的数据
     * @param userId
     * @param uid
     * @param listener
     */
    public void requestGetFocus(String userId, String uid, final OnCompleteDataListener<FocusEntry> listener) {
        String url = ConfigUrl.FOCUS_READ + "&user_id=" + userId + "&uid=" + uid;
        mGetBuilder.url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        if (statusCode == STATUS_CODE_SUCCESS) {
                            Gson gson = new Gson();
                            FocusEntry focus = gson.fromJson(response, FocusEntry.class);

                            if (listener != null) {
                                listener.onComplete(focus);
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (null != listener) {
                            listener.onError(statusCode);
                        }

                    }
                });
    }

    /**
     * 添加关注
     * @param userId
     * @param uid
     * @param listener
     */
    public void requestAddFocus(String userId, String uid, final OnCompleteDataListener<FocusEntry> listener) {
        String url = ConfigUrl.FOCUS_ADD + "&user_id=" + userId + "&uid=" + uid;
        mGetBuilder.url(url)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        if (statusCode == STATUS_CODE_SUCCESS) {
                            Gson gson = new Gson();
                            FocusEntry focus = gson.fromJson(response, FocusEntry.class);

                            if (listener != null) {
                                listener.onComplete(focus);
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (null != listener) {
                            listener.onError(statusCode);
                        }

                    }
                });
    }

    /**
     * 取消关注
     * @param focusId
     * @param listener
     */
    public void requestCancelFocus(String focusId, final OnCompleteDataListener<Integer> listener) {
        String url = ConfigUrl.FOCUS_CANCEL + "&focus_id=" + focusId;
        mGetBuilder.url(url)
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
                        if (null != listener) {
                            listener.onError(statusCode);
                        }

                    }
                });
    }

}
