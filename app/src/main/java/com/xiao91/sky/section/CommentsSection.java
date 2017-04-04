package com.xiao91.sky.section;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.xiao91.sky.bean.CommentEntry;

/**
 * 评论section分组
 * Created by xiao on 2017/1/11 0011.
 */

public class CommentsSection extends SectionEntity<CommentEntry> {

    private boolean isMore;

    public CommentsSection(boolean isHeader, String header, boolean isMore) {
        super(isHeader, header);
        this.isMore = isMore;
    }

    public CommentsSection(CommentEntry commentEntry) {
        super(commentEntry);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }
}
