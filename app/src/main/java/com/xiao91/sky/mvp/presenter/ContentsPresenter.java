package com.xiao91.sky.mvp.presenter;

import com.xiao91.sky.bean.Contents;
import com.xiao91.sky.bean.GoodOrBadCount;
import com.xiao91.sky.mvp.model.ContentsModel;
import com.xiao91.sky.mvp.model.OnCompleteDataListener;
import com.xiao91.sky.mvp.view.ContentsView;

/**
 * 主页
 * Created by xiao on 2017/1/5 0005.
 */

public class ContentsPresenter extends MVPBasePresenter<ContentsView> {

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
        contentsModel.requestData(type, new OnCompleteDataListener<Contents>() {
            @Override
            public void onComplete(Contents result) {
                if (result == null) {
                    contentsView.onShowEmptyData();
                }else {
                    contentsView.onShowData(result);
                }
            }

            @Override
            public void onError(int errorCode) {
                switch (errorCode) {
                    case 500:
                        contentsView.onShowServerError();
                        break;
                    case -1:
                        contentsView.onShowNetError();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 更多数据
     * @param type
     * @param currentCount
     */
    public void requestMoreData(int type, int currentCount) {
        contentsModel.requestMoreData(type, currentCount, new OnCompleteDataListener<Contents>() {
            @Override
            public void onComplete(Contents result) {
                contentsView.onShowMoreData(result);
            }

            @Override
            public void onError(int errorCode) {
                switch (errorCode) {
                    case 500:
                        contentsView.onShowServerError();
                        break;
                    case -1:
                        contentsView.onShowNetError();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 更新点赞
     * @param contentsId
     */
    public void requestUpdateGoodCount(String contentsId) {
        contentsModel.requestUpdateGoodCount(contentsId, new OnCompleteDataListener<GoodOrBadCount>() {
            @Override
            public void onComplete(GoodOrBadCount result) {
                contentsView.onShowUpdateGoodCount(result);
            }

            @Override
            public void onError(int errorCode) {
                // ...
            }
        });
    }

    /**
     * 更新点踩
     * @param contentsId
     */
    public void requestUpdateBadCount(String contentsId) {
        contentsModel.requestUpdateBadCount(contentsId, new OnCompleteDataListener<GoodOrBadCount>() {
            @Override
            public void onComplete(GoodOrBadCount result) {
                contentsView.onShowUpdateBadCount(result);
            }

            @Override
            public void onError(int errorCode) {
               // ...
            }
        });
    }
}
