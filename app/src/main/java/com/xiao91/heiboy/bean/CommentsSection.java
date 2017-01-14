package com.xiao91.heiboy.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 评论section
 * Created by xiao on 2017/1/11 0011.
 */

public class CommentsSection extends SectionEntity<CommentInfo> {

    private boolean isMore;

    public CommentsSection(boolean isHeader, String header, boolean isMore) {
        super(isHeader, header);
        this.isMore = isMore;
    }

    public CommentsSection(CommentInfo commentInfo) {
        super(commentInfo);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }
}
