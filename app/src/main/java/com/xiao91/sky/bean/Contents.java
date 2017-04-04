package com.xiao91.sky.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 内容
 * Created by xiao on 2017/1/10 0005.
 *
 */

public class Contents {
    public int ret;
    public Data data;
    public String msg;

    public Contents(int ret, Data data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    public static class Data {
        public String totalCount;
        public int currentCount;
        public List<ContentsInfo> contents;

        public Data(String totalCount, int currentCount, List<ContentsInfo> contents) {
            this.totalCount = totalCount;
            this.currentCount = currentCount;
            this.contents = contents;
        }

        public static class ContentsInfo implements Parcelable {
            @SerializedName("content_id")
            public String contentId;
            @SerializedName("user_id")
            public String userId;
            public String content;
            @SerializedName("content_describe")
            public String contentDescribe;
            public String title;
            public String type;
            @SerializedName("good_count")
            public String goodCount;
            @SerializedName("bad_count")
            public String badCount;
            @SerializedName("comment_count")
            public String commentCount;
            @SerializedName("share_count")
            public String shareCount;
            @SerializedName("create_time")
            public String createTime;
            public String username;
            @SerializedName("user_photo")
            public String userPhoto;

            public ContentsInfo(String contentId, String userId, String content, String contentDescribe, String title, String type, String goodCount, String badCount, String commentCount, String shareCount, String createTime, String username, String userPhoto) {
                this.contentId = contentId;
                this.userId = userId;
                this.content = content;
                this.contentDescribe = contentDescribe;
                this.title = title;
                this.type = type;
                this.goodCount = goodCount;
                this.badCount = badCount;
                this.commentCount = commentCount;
                this.shareCount = shareCount;
                this.createTime = createTime;
                this.username = username;
                this.userPhoto = userPhoto;
            }

            protected ContentsInfo(Parcel in) {
                contentId = in.readString();
                userId = in.readString();
                content = in.readString();
                contentDescribe = in.readString();
                title = in.readString();
                type = in.readString();
                goodCount = in.readString();
                badCount = in.readString();
                commentCount = in.readString();
                shareCount = in.readString();
                createTime = in.readString();
                username = in.readString();
                userPhoto = in.readString();
            }

            public static final Creator<ContentsInfo> CREATOR = new Creator<ContentsInfo>() {
                @Override
                public ContentsInfo createFromParcel(Parcel in) {
                    return new ContentsInfo(in);
                }

                @Override
                public ContentsInfo[] newArray(int size) {
                    return new ContentsInfo[size];
                }
            };

            @Override
            public String toString() {
                return "ContentsInfo{" +
                        "contentId='" + contentId + '\'' +
                        ", userId='" + userId + '\'' +
                        ", content='" + content + '\'' +
                        ", contentDescribe='" + contentDescribe + '\'' +
                        ", title='" + title + '\'' +
                        ", type='" + type + '\'' +
                        ", goodCount='" + goodCount + '\'' +
                        ", badCount='" + badCount + '\'' +
                        ", commentCount='" + commentCount + '\'' +
                        ", shareCount='" + shareCount + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", username='" + username + '\'' +
                        ", userPhoto='" + userPhoto + '\'' +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(contentId);
                parcel.writeString(userId);
                parcel.writeString(content);
                parcel.writeString(contentDescribe);
                parcel.writeString(title);
                parcel.writeString(type);
                parcel.writeString(goodCount);
                parcel.writeString(badCount);
                parcel.writeString(commentCount);
                parcel.writeString(shareCount);
                parcel.writeString(createTime);
                parcel.writeString(username);
                parcel.writeString(userPhoto);
            }
        }
    }
}
