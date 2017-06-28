package com.quanmin.sky.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 更新下载
 * Created by xiao on 2017/1/4 0004.
 */

public class ApkEntry {

    public int ret;
    public ApkInfo data;
    public String msg;

    @Override
    public String toString() {
        return "ApkEntry{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class ApkInfo {
        @SerializedName("version_name")
        public String versionName;
        @SerializedName("version_code")
        public String versionCode;
        @SerializedName("version_desc")
        public String versionDesc;
        @SerializedName("version_url")
        public String downloadUrl;

        public ApkInfo(String versionName, String versionCode, String versionDesc, String downloadUrl) {
            this.versionName = versionName;
            this.versionCode = versionCode;
            this.versionDesc = versionDesc;
            this.downloadUrl = downloadUrl;
        }

        @Override
        public String toString() {
            return "ApkInfo{" +
                    ", versionName='" + versionName + '\'' +
                    ", versionCode='" + versionCode + '\'' +
                    ", versionDesc='" + versionDesc + '\'' +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    '}';
        }
    }


}
