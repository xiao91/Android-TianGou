package com.xiao91.sky.bean;

/**
 * 取消关注
 * Created by xl on 2017/2/17 0017.
 */

public class CancelFocus {
    
    public int ret;
    public FocusData data;
    public String msg;

    public CancelFocus(int ret, FocusData data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    public static class FocusData {
        public int row;
        public int code;
        public String desc;

        public FocusData(int row, int code, String desc) {
            this.row = row;
            this.code = code;
            this.desc = desc;
        }
    }
}
