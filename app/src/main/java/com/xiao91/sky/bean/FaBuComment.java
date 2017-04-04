package com.xiao91.sky.bean;

/**
 * 发布评论
 * Created by xiao on 2017/1/13 0013.
 */

public class FaBuComment {
    public int ret;
    public CommentEntry data;
    public String msg;

    public FaBuComment(int ret, CommentEntry data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

}
