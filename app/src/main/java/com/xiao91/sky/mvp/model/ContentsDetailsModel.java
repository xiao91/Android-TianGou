package com.xiao91.sky.mvp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;
import com.xiao91.sky.bean.Comments;
import com.xiao91.sky.bean.ContentsDetail;
import com.xiao91.sky.bean.FaBuComment;
import com.xiao91.sky.bean.GoodOrBadCount;
import com.xiao91.sky.utils.TGUrls;

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
        String url = TGUrls.COMMENT_HOT_NEW + "&content_id=" + contentsId + "&user_id=" + userId;
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
                            listener.onError(500);
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
        String url = TGUrls.COMMENT_HOT_NEW + "&content_id=" + contentsId + "&create_time" + createTime;
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
                            listener.onError(500);
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
        String url = TGUrls.COMMENT_ADD + "&content_id=" + contentsId +
                "&user_id=" + userId + "&comment_detail=" + content;

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
                            listener.onError(500);
                        }
                    }
                });

    }

    public void requestUpdateGoodCount(final String contentId, final OnCompleteDataListener<GoodOrBadCount> result) {
        String url = TGUrls.CONTENT_UPDATE_GOOD_COUNT + "&content_id=" + contentId;

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
                            result.onError(500);
                        }
                    }
                });
    }

    public void requestUpdateBadCount(final String contentId, final OnCompleteDataListener<GoodOrBadCount> result) {
        String url = TGUrls.CONTENT_UPDATE_BAD_COUNT + "&content_id=" + contentId;

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
                            result.onError(500);
                        }
                    }
                });
    }

}
