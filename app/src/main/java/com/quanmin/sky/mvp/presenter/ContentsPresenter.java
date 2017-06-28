package com.quanmin.sky.mvp.presenter;

import com.quanmin.sky.bean.ContentEntry;
import com.quanmin.sky.mvp.base.BaseMvpPresenter;
import com.quanmin.sky.mvp.base.OnCompleteDataListener;
import com.quanmin.sky.mvp.model.ContentsModel;
import com.quanmin.sky.mvp.view.ContentsView;

/**
 * 主页
 * Created by xiao on 2017/1/5 0005.
 */

public class ContentsPresenter extends BaseMvpPresenter<ContentsView> {

    private ContentsView contentsView;
    private final ContentsModel contentsModel;

    public ContentsPresenter(ContentsView contentsView) {
        this.contentsView = contentsView;

        contentsModel = new ContentsModel();
    }

    /**
     * 请求数据
     * @param type
     */
    public void requestData(int type) {
        contentsView.onShowProgressbar();
        contentsModel.requestData(type, new OnCompleteDataListener<ContentEntry>() {
            @Override
            public void onComplete(ContentEntry result) {
                contentsView.onHideProgressbar();
                if (result.data == null) {
                    contentsView.onShowEmptyData();
                }else {
                    contentsView.onShowData(result);
                }
            }

            @Override
            public void onError(int errorCode) {
                contentsView.onHideProgressbar();
                contentsView.onShowError(errorCode);
            }
        });

    }

    /**
     * 更多数据
     * @param type
     * @param currentCount
     */
    public void requestMoreData(int type, int currentCount) {
        contentsModel.requestMoreData(type, currentCount, new OnCompleteDataListener<ContentEntry>() {
            @Override
            public void onComplete(ContentEntry result) {
                contentsView.onShowMoreData(result);
            }

            @Override
            public void onError(int errorCode) {
                contentsView.onShowMoreDataError(errorCode);
            }
        });
    }

    /**
     * 更新点赞
     * @param countId
     */
    public void requestUpdateGoodCount(String countId, String userId, String deviceId) {
        contentsModel.requestUpdateGoodCount(countId, userId, deviceId, new OnCompleteDataListener<Integer>() {
            @Override
            public void onComplete(Integer code) {
                contentsView.onShowUpdateGoodCount(code);
            }

            @Override
            public void onError(int errorCode) {
                contentsView.onShowMoreDataError(errorCode);
            }
        });
    }

    /**
     * 更新点踩
     * @param countId
     */
    public void requestUpdateBadCount(String countId, String userId, String deviceId) {
        contentsModel.requestUpdateBadCount(countId, userId, deviceId, new OnCompleteDataListener<Integer>() {
            @Override
            public void onComplete(Integer code) {
                contentsView.onShowUpdateBadCount(code);
            }

            @Override
            public void onError(int errorCode) {
                contentsView.onShowMoreDataError(errorCode);
            }
        });
    }
}
