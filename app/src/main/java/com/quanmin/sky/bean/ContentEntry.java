package com.quanmin.sky.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 内容
 * Created by xiao on 2017/1/10 0005.
 *
 */

public class ContentEntry {

    public int ret;
    public String msg;
    public ContentData data;

    public ContentEntry(int ret, ContentData data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }
    
    public static class ContentData {
        public String total;
        @SerializedName("current_count")
        public int currentCount;
        public List<Content> content;

        public ContentData(String total, int currentCount, List<Content> content) {
            this.total = total;
            this.currentCount = currentCount;
            this.content = content;
        }

        public static class Content implements Parcelable {
            @SerializedName("user_head")
            public String userHead;
            public String username;
            @SerializedName("content_id")
            public String contentId;
            @SerializedName("user_id")
            public String userId;
            @SerializedName("content_detail")
            public String contentDetail;
            @SerializedName("content_desc")
            public String contentDesc;
            @SerializedName("content_source_url")
            public String contentSourceUrl;
            @SerializedName("content_title")
            public String contentTitle;
            @SerializedName("content_type")
            public String contentType;
            @SerializedName("create_time")
            public String createTime;

            @SerializedName("count_id")
            public String countId;

            @SerializedName("g_dids")
            public String gDids;
            @SerializedName("g_uids")
            public String gUids;

            @SerializedName("b_uids")
            public String bUids;
            @SerializedName("b_dids")
            public String bDids;

            @SerializedName("good_count")
            public String goodCount;
            @SerializedName("bad_count")
            public String badCount;
            @SerializedName("comment_count")
            public String commentCount;
            @SerializedName("share_count")
            public String shareCount;

            public Content(String userHead, String username, String contentId, String userId,
                           String contentDetail, String contentDesc, String contentSourceUrl,
                           String contentTitle, String contentType, String createTime, String countId,
                           String gDids, String gUids, String bUids, String bDids, String goodCount, String badCount, String commentCount, String shareCount) {
                this.userHead = userHead;
                this.username = username;
                this.contentId = contentId;
                this.userId = userId;
                this.contentDetail = contentDetail;
                this.contentDesc = contentDesc;
                this.contentSourceUrl = contentSourceUrl;
                this.contentTitle = contentTitle;
                this.contentType = contentType;
                this.createTime = createTime;
                this.countId = countId;
                this.gDids = gDids;
                this.gUids = gUids;
                this.bUids = bUids;
                this.bDids = bDids;
                this.goodCount = goodCount;
                this.badCount = badCount;
                this.commentCount = commentCount;
                this.shareCount = shareCount;
            }

            protected Content(Parcel in) {
                userHead = in.readString();
                username = in.readString();
                contentId = in.readString();
                userId = in.readString();
                contentDetail = in.readString();
                contentDesc = in.readString();
                contentSourceUrl = in.readString();
                contentTitle = in.readString();
                contentType = in.readString();
                createTime = in.readString();
                countId = in.readString();
                gDids = in.readString();
                gUids = in.readString();
                bUids = in.readString();
                bDids = in.readString();
                goodCount = in.readString();
                badCount = in.readString();
                commentCount = in.readString();
                shareCount = in.readString();
            }

            public static final Creator<Content> CREATOR = new Creator<Content>() {
                @Override
                public Content createFromParcel(Parcel in) {
                    return new Content(in);
                }

                @Override
                public Content[] newArray(int size) {
                    return new Content[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(userHead);
                dest.writeString(username);
                dest.writeString(contentId);
                dest.writeString(userId);
                dest.writeString(contentDetail);
                dest.writeString(contentDesc);
                dest.writeString(contentSourceUrl);
                dest.writeString(contentTitle);
                dest.writeString(contentType);
                dest.writeString(createTime);
                dest.writeString(countId);
                dest.writeString(gDids);
                dest.writeString(gUids);
                dest.writeString(bDids);
                dest.writeString(bUids);
                dest.writeString(goodCount);
                dest.writeString(badCount);
                dest.writeString(commentCount);
                dest.writeString(shareCount);
            }
        }
    }
}
