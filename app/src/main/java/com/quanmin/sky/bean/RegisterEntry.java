package com.quanmin.sky.bean;

/**
 * 注册
 * Created by xiao on 2017/3/19.
 */

public class RegisterEntry {

    public int ret;
    public Register data;
    public String msg;

    public RegisterEntry(int ret, Register data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    public static class Register {
        public int code;

        public String info;

        public Register(int code, String info) {
            this.code = code;
            this.info = info;
        }
    }


}
