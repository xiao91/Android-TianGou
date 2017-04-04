package com.xiao91.sky.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 注册
 * Created by xiao on 2017/3/19.
 */

public class UserRegisterEntry {

    public int ret;
    public UserRegister data;
    public String msg;

    public static class UserRegister {

        public int code;
        public String info;
        public String phone;
        public String password;
        @SerializedName("create_time")
        public String createTime;
        public String userId;

        public UserRegister(int code, String info, String phone, String password, String createTime, String userId) {
            this.code = code;
            this.info = info;
            this.phone = phone;
            this.password = password;
            this.createTime = createTime;
            this.userId = userId;
        }
    }

}
