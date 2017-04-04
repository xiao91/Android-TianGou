package com.xiao91.sky.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 评论内容详情
 * Created by xiao on 2017/1/12 0012.
 */

public class ContentsDetail {

    public int ret;
    public Data data;
    public String msg;

    public ContentsDetail(int ret, Data data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    public static class Data {
        public String totalComment;
        // 该用户发表的帖子数量
        public String userContentsCount;
        // 该用户粉丝数量
        public String userFollowersCount;
        public List<CommentEntry> hotComments;
        public List<CommentEntry> newComments;

        public Data(String totalComment, String userContentsCount, String userFollowersCount,
                    List<CommentEntry> hotComments, List<CommentEntry> newComments) {
            this.totalComment = totalComment;
            this.userContentsCount = userContentsCount;
            this.userFollowersCount = userFollowersCount;
            this.hotComments = hotComments;
            this.newComments = newComments;
        }
    }
}
