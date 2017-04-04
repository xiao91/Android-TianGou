package com.xiao91.sky.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 评论详情
 * Created by xiao on 2017/1/13 0013.
 */

public class CommentEntry {
    @SerializedName("user_photo")
    public String userPhoto;
    public String username;
    @SerializedName("comment_id")
    public String commentId;
    @SerializedName("content_id")
    public String contentId;
    @SerializedName("user_id")
    public String userId;
    @SerializedName("comment_detail")
    public String commentDetail;
    // 该用户的评论被点赞的次数
    @SerializedName("user_good_count")
    public String userGoodCount;
    @SerializedName("create_time")
    public String createTime;

    public CommentEntry(String userPhoto, String username, String commentId, String contentId,
                        String userId, String commentDetail, String userGoodCount, String createTime) {
        this.userPhoto = userPhoto;
        this.username = username;
        this.commentId = commentId;
        this.contentId = contentId;
        this.userId = userId;
        this.commentDetail = commentDetail;
        this.userGoodCount = userGoodCount;
        this.createTime = createTime;
    }
}
