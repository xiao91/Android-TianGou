package com.xiao91.sky.bean;

/**
 *
 * 登陆
 *
 * Created by xiao on 2017/1/3 0003.
 *
 */

public class UserLoginEntry {

    public int ret;
    public UserInfo data;
    public String msg;

    public UserLoginEntry(int ret, UserInfo data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    public static class UserInfo {

        public int code;
        public String info;
        public String userId;

        public UserInfo(int code, String info, String userId) {
            this.code = code;
            this.info = info;
            this.userId = userId;
        }
    }
}
