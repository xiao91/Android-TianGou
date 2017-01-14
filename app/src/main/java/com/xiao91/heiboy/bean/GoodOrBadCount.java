package com.xiao91.heiboy.bean;

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

    @Override
    public String toString() {
        return "GoodOrBadCount{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class CountInfo {
        public String info;
        public int contentsId;

        public CountInfo(String info, int contentsId) {
            this.info = info;
            this.contentsId = contentsId;
        }

        @Override
        public String toString() {
            return "CountInfo{" +
                    "info='" + info + '\'' +
                    ", contentsId=" + contentsId +
                    '}';
        }
    }
}
