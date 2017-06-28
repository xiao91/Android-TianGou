package com.quanmin.sky.mvp.view;

import com.quanmin.sky.bean.AddCommentEntry;
import com.quanmin.sky.bean.FocusEntry;
import com.quanmin.sky.bean.CommentEntry;
import com.quanmin.sky.mvp.base.IBaseMvpView;

/**
 *
 * Created by xiao on 2017/1/12 0012.
 */

public interface ContentsDetailView extends IBaseMvpView<CommentEntry> {

    /**
     * 更多数据
     * @param comments 评论
     */
    void onShowMoreData(CommentEntry comments);

    /**
     * 显示发布的评论
     * @param addCommentEntry 添加评论
     */
    void onShowAddComment(AddCommentEntry addCommentEntry);

    void onShowAddCommentError();

    /**
     * 点赞
     * @param code 提示码，0:没有更新，1：更新成功，-1：更新失败
     */
    void onShowUpdateGoodCount(Integer code);

    /**
     * 点踩
     * @param code 提示码，0:没有更新，1：更新成功，-1：更新失败
     */
    void onShowUpdateBadCount(Integer code);

    /**
     * 获取关注的信息
     * @param focusEntry 关注
     */
    void onShowGetFocus(FocusEntry focusEntry);

    /**
     * 添加关注
     * @param focusEntry 添加关注
     */
    void onShowAddFocus(FocusEntry focusEntry);

    /**
     * 取消关注
     * @param code 1取消关注成功，-1取消关注失败
     *
     */
    void onShowCancelFocus(Integer code);
}
