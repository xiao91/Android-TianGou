package com.quanmin.sky.bean;

/**
 * 发布评论
 * Created by xiao on 2017/1/13 0013.
 */

public class AddCommentEntry {
    public int ret;
    public CommentEntry.CommentData.CommentDetail data;
    public String msg;

    public AddCommentEntry(int ret, CommentEntry.CommentData.CommentDetail data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

}
