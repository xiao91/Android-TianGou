package com.xiao91.heiboy.bean;

/**
 * 发布评论
 * Created by xiao on 2017/1/13 0013.
 */

public class FaBuComment {
    public int ret;
    public CommentInfo data;
    public String msg;

    public FaBuComment(int ret, CommentInfo data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "FaBuComment{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

}
