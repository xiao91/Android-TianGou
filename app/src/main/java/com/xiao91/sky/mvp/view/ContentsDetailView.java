package com.xiao91.sky.mvp.view;

import com.xiao91.sky.bean.Comments;
import com.xiao91.sky.bean.ContentsDetail;
import com.xiao91.sky.bean.FaBuComment;
import com.xiao91.sky.bean.GoodOrBadCount;

/**
 *
 * Created by xiao on 2017/1/12 0012.
 */

public interface ContentsDetailView extends IBaseView<ContentsDetail>, FocusView {

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
     */
    void showFaBuCommentError();

    void showUpdateGoodCount(GoodOrBadCount goodOrBadCount);

    void showUpdateBadCount(GoodOrBadCount goodOrBadCount);

}
