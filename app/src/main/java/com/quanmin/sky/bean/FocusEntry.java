package com.quanmin.sky.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 添加关注
 *
 * Created by xl on 2017/2/17 0017.
 *
 */

public class FocusEntry {
    
    public int ret;
    public FocusData data;
    public String msg;

    public static class FocusData {
        // 1成功
        public int code;
        public String info;
        @SerializedName("focus_id")
        public String focusId;
    }
}
