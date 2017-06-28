package com.quanmin.sky.bean;

/**
 * 用户更新信息
 * Created by xiao on 2017/1/3 0003.
 *
 */

public class UserUpdateEntry {

    public int ret;
    public UserUpdate data;
    public String msg;

    public UserUpdateEntry(int ret, UserUpdate data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    public static class UserUpdate {
        public int code;
        public String info;

        public UserUpdate(int code, String info) {
            this.code = code;
            this.info = info;
        }
    }
}
