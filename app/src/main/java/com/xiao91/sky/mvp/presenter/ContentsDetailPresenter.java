package com.xiao91.sky.mvp.presenter;

import com.xiao91.sky.bean.Comments;
import com.xiao91.sky.bean.ContentsDetail;
import com.xiao91.sky.bean.FaBuComment;
import com.xiao91.sky.bean.GoodOrBadCount;
import com.xiao91.sky.mvp.model.ContentsDetailsModel;
import com.xiao91.sky.mvp.model.OnCompleteDataListener;
import com.xiao91.sky.mvp.view.ContentsDetailView;

/**
 * 内容详情
 * Created by xiao on 2017/1/12 0012.
 */

public class ContentsDetailPresenter extends MVPBasePresenter<ContentsDetailView> {

    private ContentsDetailView contentsDetailView;
    private final ContentsDetailsModel contentsDetailsModel;

    public ContentsDetailPresenter(ContentsDetailView contentsDetailView) {
        this.contentsDetailView = contentsDetailView;

        contentsDetailsModel = new ContentsDetailsModel();
    }

    public void requestComments(String userId, String contentsId) {
        contentsDetailsModel.requestComments(userId, contentsId, new OnCompleteDataListener<ContentsDetail>() {
            @Override
            public void onComplete(ContentsDetail result) {
                contentsDetailView.showData(result);
            }

            @Override
            public void onError(int errorCode) {
                contentsDetailView.showErrorMessage(errorCode);
            }
        });
    }

    /**
     * 更多数据
     * @param contentsId
     * @param createTime
     */
    public void requestMoreComments(String contentsId, String createTime) {
        contentsDetailsModel.requestMoreComments(contentsId, createTime, new OnCompleteDataListener<Comments>() {
            @Override
            public void onComplete(Comments result) {
                contentsDetailView.showMoreData(result);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    /**
     * 发布评论
     * @param contentsId
     * @param userId
     * @param content
     */
    public void requestFaBuComment(String contentsId, String userId, String content) {
        contentsDetailsModel.requestFaBuComment(contentsId, userId, content, new OnCompleteDataListener<FaBuComment>() {
            @Override
            public void onComplete(FaBuComment result) {
                contentsDetailView.showFaBuComment(result);
            }

            @Override
            public void onError(int errorCode) {
                contentsDetailView.showFaBuCommentError();
            }
        });
    }

    public void requestUpdateGoodCount(String contentsId) {
        contentsDetailsModel.requestUpdateGoodCount(contentsId, new OnCompleteDataListener<GoodOrBadCount>() {
            @Override
            public void onComplete(GoodOrBadCount result) {
                contentsDetailView.showUpdateGoodCount(result);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    public void requestUpdateBadCount(String contentsId) {
        contentsDetailsModel.requestUpdateBadCount(contentsId, new OnCompleteDataListener<GoodOrBadCount>() {
            @Override
            public void onComplete(GoodOrBadCount result) {
                contentsDetailView.showUpdateBadCount(result);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    public void requestAddFocus(String userId, String uid) {
        FocusPre presenter = new FocusPre(contentsDetailView);
        presenter.requestAddFocus(userId, uid);
    }

    public void requestCancelFocus(String focusId) {
        FocusPre presenter = new FocusPre(contentsDetailView);
        presenter.requestCancelFocus(focusId);
    }
}
