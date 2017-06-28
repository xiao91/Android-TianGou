package com.quanmin.sky.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 评论详情
 * Created by xiao on 2017/1/13 0013.
 */

public class CommentEntry {
  
    public int ret;
    public CommentData data;
    public String msg;

    public CommentEntry(int ret, CommentData data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    public static class CommentData {
        @SerializedName("works_count")
        public String worksCount;
        @SerializedName("followers_count")
        public String followersCount;
        @SerializedName("hot_comment")
        public List<CommentDetail> hotComment;
        @SerializedName("new_comment")
        public List<CommentDetail> newComment;

        public CommentData(String worksCount, String followersCount, List<CommentDetail> hotComment, List<CommentDetail> newComment) {
            this.worksCount = worksCount;
            this.followersCount = followersCount;
            this.hotComment = hotComment;
            this.newComment = newComment;
        }

        public static class CommentDetail {
            @SerializedName("user_head")
            public String userHead;
            @SerializedName("username")
            public String username;
            @SerializedName("comment_id")
            public String commentId;
            @SerializedName("content_id")
            public String contentId;
            @SerializedName("comment_detail")
            public String commentDetail;
            @SerializedName("from_uid")
            public String fromUid;
            @SerializedName("to_uid")
            public String toUid;
            @SerializedName("good_count")
            public String goodCount;
            @SerializedName("create_time")
            public String createTime;

            public CommentDetail(String userHead, String username, String commentId, String contentId,
                                 String commentDetail, String fromUid, String toUid, String goodCount, String createTime) {
                this.userHead = userHead;
                this.username = username;
                this.commentId = commentId;
                this.contentId = contentId;
                this.commentDetail = commentDetail;
                this.fromUid = fromUid;
                this.toUid = toUid;
                this.goodCount = goodCount;
                this.createTime = createTime;
            }
        }
    }
}
