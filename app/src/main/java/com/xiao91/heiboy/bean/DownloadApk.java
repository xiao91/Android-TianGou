package com.xiao91.heiboy.bean;

/**
 * 更新下载
 * Created by xiao on 2017/1/4 0004.
 */

public class DownloadApk {

    public int ret;
    public ApkInfo data;
    public String msg;

    @Override
    public String toString() {
        return "DownloadApk{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class ApkInfo {
        public String versionName;
        public int versionCode;
        public String versionDesc;
        public String downloadUrl;

        @Override
        public String toString() {
            return "ApkInfo{" +
                    "versionName='" + versionName + '\'' +
                    ", versionCode=" + versionCode +
                    ", versionDesc='" + versionDesc + '\'' +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    '}';
        }
    }


}
