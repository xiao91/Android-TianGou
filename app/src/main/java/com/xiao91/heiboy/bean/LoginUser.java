package com.xiao91.heiboy.bean;

/**
 *
 * 登陆
 *
 * Created by xiao on 2017/1/3 0003.
 *
 */

public class LoginUser {

    public int ret;
    public UserInfo data;
    public String msg;

    @Override
    public String toString() {
        return "LoginUser{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class UserInfo {

        public int code;
        public String info;
        public String userId;

        @Override
        public String toString() {
            return "UserInfo{" +
                    "code=" + code +
                    ", info='" + info + '\'' +
                    ", userId='" + userId + '\'' +
                    '}';
        }
    }
}
