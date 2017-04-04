package com.xiao91.sky.bean;

/**
 * 点赞或被踩
 * Created by xiao on 2017/1/10 0010.
 */

public class GoodOrBadCount {
    public int ret;
    public CountInfo data;
    public String msg;

    public GoodOrBadCount(int ret, CountInfo data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    public static class CountInfo {
        public String info;
        public int code;

        public CountInfo(String info, int code) {
            this.info = info;
            this.code = code;
        }

    }
}
