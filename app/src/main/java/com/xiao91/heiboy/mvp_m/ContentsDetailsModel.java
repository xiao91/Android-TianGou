package com.xiao91.heiboy.mvp_m;

import android.util.Log;

import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;
import com.xiao91.heiboy.bean.Comments;
import com.xiao91.heiboy.bean.ContentsDetail;
import com.xiao91.heiboy.bean.FaBuComment;
import com.xiao91.heiboy.bean.GoodOrBadCount;
import com.xiao91.heiboy.utils.TianGouUrls;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 内容详情：主要请求评论相关的数据
 * Created by xiao on 2017/1/12 0012.
 */

public class ContentsDetailsModel {

    private MyOkHttp okHttp = new MyOkHttp();

    /**
     * 请求评论数据
     *
     * @param userId     用户id
     * @param contentsId 该内容id
     * @param listener
     */
    public void requestComments(String userId, String contentsId, final OnCompleteDataListener<ContentsDetail> listener) {
        String url = TianGouUrls.HOST_URL + TianGouUrls.COMMENT_HOT_NEW_URL + "&contentsId=" + contentsId + "&userId=" + userId;
        okHttp.get()
                .url(url)
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("Main", "所有评论的数据：" + response);

                        Gson gson = new Gson();
                        ContentsDetail contentsDetail = gson.fromJson(response, ContentsDetail.class);
                        if (listener != null) {
                            listener.onComplete(contentsDetail);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (listener != null) {
                            listener.onError(error_msg);
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
    public void requestMoreComments(String contentsId, String createTime, final OnCompleteDataListener<Comments> listener) {
        String url = TianGouUrls.HOST_URL + TianGouUrls.COMMENT_HOT_NEW_URL + "&contentsId=" + contentsId + "&createTime" + createTime;
        okHttp.get()
                .url(url)
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("Main", "更多评论的数据：" + response);

                        Gson gson = new Gson();
                        Comments comments = gson.fromJson(response, Comments.class);
                        if (listener != null) {
                            listener.onComplete(comments);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (listener != null) {
                            listener.onError(error_msg);
                        }
                    }
                });
    }

    /**
     * 发表评论
     *
     * @param contentsId
     * @param userId
     * @param content
     * @param listener
     */
    public void requestFaBuComment(String contentsId, String userId, String content, final OnCompleteDataListener<FaBuComment> listener) {
        String url = TianGouUrls.HOST_URL + TianGouUrls.COMMENT_FABU_URL + "&contentsId=" + contentsId +
                "&userId=" + userId + "&commentDetail=" + content;

        okHttp.get()
                .url(url)
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        Log.e("Main", "发布评论：" + response);

                        Gson gson = new Gson();
                        FaBuComment faBuComment = gson.fromJson(response, FaBuComment.class);
                        if (listener != null) {
                            listener.onComplete(faBuComment);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        if (listener != null) {
                            listener.onError(error_msg);
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
