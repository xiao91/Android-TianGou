package com.xiao91.heiboy.bean;

/**
 * 评论详情
 * Created by xiao on 2017/1/13 0013.
 */

public class CommentInfo {
    public String userPhoto;
    public String username;
    public String commentId;
    public String contentsId;
    public String userId;
    public String commentDetail;
    public String userGoodCount;
    public String createTime;

    public CommentInfo(String userPhoto, String username, String commentId, String contentsId,
                       String userId, String commentDetail, String userGoodCount, String createTime) {
        this.userPhoto = userPhoto;
        this.username = username;
        this.commentId = commentId;
        this.contentsId = contentsId;
        this.userId = userId;
        this.commentDetail = commentDetail;
        this.userGoodCount = userGoodCount;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "userPhoto='" + userPhoto + '\'' +
                ", username='" + username + '\'' +
                ", commentId='" + commentId + '\'' +
                ", contentsId='" + contentsId + '\'' +
                ", userId='" + userId + '\'' +
                ", commentDetail='" + commentDetail + '\'' +
                ", userGoodCount='" + userGoodCount + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
