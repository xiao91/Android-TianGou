package com.xiao91.sky.bean;

/**
 * 添加关注
 *
 * Created by xl on 2017/2/17 0017.
 *
 */

public class AddFocus {

    public int ret;
    public FocusData data;
    public String msg;

    public AddFocus(int ret, FocusData data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AddFocus{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class FocusData {
        public int userId;
        public int uid;
        public String focusId;
        public int code;
        public String desc;

        public FocusData(int userId, int uid, String focusId, int code, String desc) {
            this.userId = userId;
            this.uid = uid;
            this.focusId = focusId;
            this.code = code;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "FocusData{" +
                    "userId=" + userId +
                    ", uid=" + uid +
                    ", focusId='" + focusId + '\'' +
                    ", code=" + code +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }
}
