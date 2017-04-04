package com.xiao91.sky.bean;

import java.util.List;

/**
 * 评论内容
 * Created by xiao on 2017/1/13 0013.
 */

public class Comments {
    public int ret;
    public String msg;
    public List<CommentEntry> data;

    public Comments(int ret, String msg, List<CommentEntry> data) {
        this.ret = ret;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
