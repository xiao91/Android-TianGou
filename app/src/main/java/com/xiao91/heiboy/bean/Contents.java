package com.xiao91.heiboy.bean;

import java.util.List;

/**
 * 内容
 * Created by xiao on 2017/1/5 0005.
 */

public class Contents {
    public int ret;
    public String msg;
    public List<ContentInfo> data;

    public Contents(int ret, String msg, List<ContentInfo> data) {
        this.ret = ret;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Contents{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class ContentInfo {
        public String topical_id;
        public String user_id;
        public String img_url;
        public String content_desc;
        public String title;
        public String type;
        public String stamp_count;
        public String praise_count;
        public String comment_count;
        public String retrans_count;
        public String create_time;
        public String user_name;
        public String user_photo;

        public ContentInfo(String user_name, String topical_id, String user_id, String img_url,
                           String content_desc, String title, String type, String stamp_count,
                           String praise_count, String comment_count, String retrans_count,
                           String create_time, String user_photo) {
            this.user_name = user_name;
            this.topical_id = topical_id;
            this.user_id = user_id;
            this.img_url = img_url;
            this.content_desc = content_desc;
            this.title = title;
            this.type = type;
            this.stamp_count = stamp_count;
            this.praise_count = praise_count;
            this.comment_count = comment_count;
            this.retrans_count = retrans_count;
            this.create_time = create_time;
            this.user_photo = user_photo;
        }

        @Override
        public String toString() {
            return "ContentInfo{" +
                    "topical_id='" + topical_id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", img_url='" + img_url + '\'' +
                    ", content_desc='" + content_desc + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", stamp_count='" + stamp_count + '\'' +
                    ", praise_count='" + praise_count + '\'' +
                    ", comment_count='" + comment_count + '\'' +
                    ", retrans_count='" + retrans_count + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", user_photo='" + user_photo + '\'' +
                    '}';
        }
    }


}
