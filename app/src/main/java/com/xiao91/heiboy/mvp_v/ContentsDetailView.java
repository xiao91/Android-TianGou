package com.xiao91.heiboy.mvp_v;

import com.xiao91.heiboy.bean.Comments;
import com.xiao91.heiboy.bean.ContentsDetail;
import com.xiao91.heiboy.bean.FaBuComment;
import com.xiao91.heiboy.bean.GoodOrBadCount;

/**
 *
 * Created by xiao on 2017/1/12 0012.
 */

public interface ContentsDetailView extends IBaseView<ContentsDetail> {

    /**
     * 更多数据
     * @param comments
     */
    void showMoreData(Comments comments);

    /**
     * 显示发布的评论
     * @param faBuComment
     */
    void showFaBuComment(FaBuComment faBuComment);

    /**
     * 发布失败
     * @param errorMsg
     */
    void showFaBuCommentError(String errorMsg);

    void showUpdateGoodCount(GoodOrBadCount goodOrBadCount);

    void showUpdateBadCount(GoodOrBadCount goodOrBadCount);

}
