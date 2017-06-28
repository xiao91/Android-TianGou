package com.quanmin.sky.bean;

import com.google.gson.annotations.SerializedName;

/**
 *
 * 登陆
 *
 * Created by xiao on 2017/1/3 0003.
 *
 */

public class LoginEntry {
    
    public int ret;
    public Login data;
    public String msg;

    public LoginEntry(int ret, Login data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    public static class Login {
        // 0成功，-1失败
        public int code;
        // 提示信息
        public String info;
        // token标识
        public String token;
        // 用户id
        @SerializedName("user_id")
        public String userId;

        public Login(int code, String info, String token, String userId) {
            this.code = code;
            this.info = info;
            this.token = token;
            this.userId = userId;
        }
    }
}
