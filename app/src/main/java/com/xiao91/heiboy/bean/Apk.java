package com.xiao91.heiboy.bean;

/**
 * 更新下载
 * Created by xiao on 2017/1/4 0004.
 */

public class Apk {

    public int ret;
    public ApkInfo data;
    public String msg;

    @Override
    public String toString() {
        return "Apk{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class ApkInfo {
        public String apkId;
        public String versionName;
        public String versionCode;
        public String versionDesc;
        public String downloadUrl;

        public ApkInfo(String apkId, String versionName, String versionCode, String versionDesc, String downloadUrl) {
            this.apkId = apkId;
            this.versionName = versionName;
            this.versionCode = versionCode;
            this.versionDesc = versionDesc;
            this.downloadUrl = downloadUrl;
        }

        @Override
        public String toString() {
            return "ApkInfo{" +
                    "apkId='" + apkId + '\'' +
                    ", versionName='" + versionName + '\'' +
                    ", versionCode='" + versionCode + '\'' +
                    ", versionDesc='" + versionDesc + '\'' +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    '}';
        }
    }


}
