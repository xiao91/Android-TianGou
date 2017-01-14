package com.xiao91.heiboy.bean;

import java.util.List;

/**
 * 内容详情
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

    @Override
    public String toString() {
        return "ContentsDetail{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class Data {
        public String totalComment;
        public String userContentsCount;
        public String userFollowersCount;
        public List<CommentInfo> hotComments;
        public List<CommentInfo> newComments;

        public Data(String totalComment, String userContentsCount, String userFollowersCount,
                    List<CommentInfo> hotComments, List<CommentInfo> newComments) {
            this.totalComment = totalComment;
            this.userContentsCount = userContentsCount;
            this.userFollowersCount = userFollowersCount;
            this.hotComments = hotComments;
            this.newComments = newComments;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "totalComment='" + totalComment + '\'' +
                    ", userContentsCount='" + userContentsCount + '\'' +
                    ", userFollowersCount='" + userFollowersCount + '\'' +
                    ", hotComments=" + hotComments +
                    ", newComments=" + newComments +
                    '}';
        }
    }
}
