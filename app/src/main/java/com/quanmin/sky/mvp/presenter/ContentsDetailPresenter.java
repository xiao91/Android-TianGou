package com.quanmin.sky.mvp.presenter;

import com.quanmin.sky.bean.AddCommentEntry;
import com.quanmin.sky.bean.FocusEntry;
import com.quanmin.sky.bean.CommentEntry;
import com.quanmin.sky.mvp.base.BaseMvpPresenter;
import com.quanmin.sky.mvp.base.OnCompleteDataListener;
import com.quanmin.sky.mvp.model.ContentsDetailsModel;
import com.quanmin.sky.mvp.view.ContentsDetailView;

/**
 * 内容详情
 * Created by xiao on 2017/1/12 0012.
 */

public class ContentsDetailPresenter extends BaseMvpPresenter<ContentsDetailView> {

    private ContentsDetailView contentsDetailView;
    private final ContentsDetailsModel contentsDetailsModel;

    public ContentsDetailPresenter(ContentsDetailView contentsDetailView) {
        this.contentsDetailView = contentsDetailView;

        contentsDetailsModel = new ContentsDetailsModel();
    }

    public void requestComments(String userId, String contentsId) {
        contentsDetailsModel.requestComments(userId, contentsId, new OnCompleteDataListener<CommentEntry>() {
            @Override
            public void onComplete(CommentEntry result) {
                contentsDetailView.onShowData(result);
            }

            @Override
            public void onError(int errorCode) {
                contentsDetailView.onShowError(errorCode);
            }
        });
    }

    /**
     * 更多数据
     * @param contentsId
     * @param createTime
     */
    public void requestMoreComments(String contentsId, String createTime) {
        contentsDetailsModel.requestMoreComments(contentsId, createTime, new OnCompleteDataListener<CommentEntry>() {
            @Override
            public void onComplete(CommentEntry result) {
                contentsDetailView.onShowMoreData(result);
            }

            @Override
            public void onError(int errorCode) {
                contentsDetailView.onShowError(errorCode);
            }
        });
    }

    /**
     * 发布评论
     */
    public void requestAddComment(String contentsId, String content, String from_uid, String to_uid) {
        contentsDetailsModel.requestAddComment(contentsId, content, from_uid, to_uid, new OnCompleteDataListener<AddCommentEntry>() {
            @Override
            public void onComplete(AddCommentEntry result) {
                contentsDetailView.onShowAddComment(result);
            }

            @Override
            public void onError(int errorCode) {
                contentsDetailView.onShowAddCommentError();
            }
        });
    }

    /**
     * 更新点赞
     */
    public void requestUpdateGoodCount(String countId, String userId, String deviceId) {
        contentsDetailsModel.requestUpdateGoodCount(countId, userId, deviceId, new OnCompleteDataListener<Integer>() {
            @Override
            public void onComplete(Integer code) {
                contentsDetailView.onShowUpdateGoodCount(code);
            }

            @Override
            public void onError(int errorCode) {
                contentsDetailView.onShowError(errorCode);
            }
        });
    }

    /**
     * 更新点踩
     */
    public void requestUpdateBadCount(String countId, String userId, String deviceId) {
        contentsDetailsModel.requestUpdateBadCount(countId, userId, deviceId, new OnCompleteDataListener<Integer>() {
            @Override
            public void onComplete(Integer code) {
                contentsDetailView.onShowUpdateBadCount(code);
            }

            @Override
            public void onError(int errorCode) {
                contentsDetailView.onShowError(errorCode);
            }
        });
    }

    /**
     * 获取是否得到关注的数据
     * @param userId
     * @param uid
     */
    public void requestGetFocus(String userId, String uid) {
        contentsDetailsModel.requestGetFocus(userId, uid, new OnCompleteDataListener<FocusEntry>() {
            @Override
            public void onComplete(FocusEntry data) {
                contentsDetailView.onShowGetFocus(data);
            }

            @Override
            public void onError(int code) {
                contentsDetailView.onShowError(code);
            }
        });
    }

    public void requestAddFocus(String userId, String uid) {
        contentsDetailsModel.requestAddFocus(userId, uid, new OnCompleteDataListener<FocusEntry>() {
            @Override
            public void onComplete(FocusEntry data) {
                contentsDetailView.onShowAddFocus(data);
            }

            @Override
            public void onError(int code) {
                contentsDetailView.onShowError(code);
            }
        });
    }

    public void requestCancelFocus(String focusId) {
       contentsDetailsModel.requestCancelFocus(focusId, new OnCompleteDataListener<Integer>() {
           @Override
           public void onComplete(Integer code) {
               contentsDetailView.onShowCancelFocus(code);
           }

           @Override
           public void onError(int code) {
               contentsDetailView.onShowError(code);
           }
       });
    }
}
