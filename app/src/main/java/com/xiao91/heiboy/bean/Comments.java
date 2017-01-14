package com.xiao91.heiboy.bean;

import java.util.List;

/**
 * 评论内容
 * Created by xiao on 2017/1/13 0013.
 */

public class Comments {
    public int ret;
    public String msg;
    public List<CommentInfo> data;

    public Comments(int ret, String msg, List<CommentInfo> data) {
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
