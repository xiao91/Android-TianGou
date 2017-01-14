package com.xiao91.heiboy.bean;

import android.os.Parcel;
import android.os.Parcelable;

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

    @Override
    public String toString() {
        return "Contents{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class Data {
        public int currentCount;
        public List<ContentsInfo> contents;

        public Data(int currentCount, List<ContentsInfo> contents) {
            this.currentCount = currentCount;
            this.contents = contents;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "currentCount=" + currentCount +
                    ", contents=" + contents +
                    '}';
        }

        public static class ContentsInfo implements Parcelable {
            public String contentsId;
            public String userId;
            public String imgUrlOrContent;
            public String contentDesc;
            public String title;
            public String type;
            public String goodCount;
            public String badCount;
            public String commentCount;
            public String shareCount;
            public String createTime;
            public String username;
            public String userPhoto;

            public ContentsInfo(String contentsId, String userId, String imgUrlOrContent, String contentDesc, String title, String type, String goodCount, String badCount, String commentCount, String shareCount, String createTime, String username, String userPhoto) {
                this.contentsId = contentsId;
                this.userId = userId;
                this.imgUrlOrContent = imgUrlOrContent;
                this.contentDesc = contentDesc;
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
                contentsId = in.readString();
                userId = in.readString();
                imgUrlOrContent = in.readString();
                contentDesc = in.readString();
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
                        "contentsId='" + contentsId + '\'' +
                        ", userId='" + userId + '\'' +
                        ", imgUrlOrContent='" + imgUrlOrContent + '\'' +
                        ", contentDesc='" + contentDesc + '\'' +
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
                parcel.writeString(contentsId);
                parcel.writeString(userId);
                parcel.writeString(imgUrlOrContent);
                parcel.writeString(contentDesc);
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
