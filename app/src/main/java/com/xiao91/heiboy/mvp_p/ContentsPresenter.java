package com.xiao91.heiboy.mvp_p;

import com.xiao91.heiboy.bean.Contents;
import com.xiao91.heiboy.mvp_m.ContentsModel;
import com.xiao91.heiboy.mvp_m.OnCompleteDataListener;
import com.xiao91.heiboy.mvp_v.ContentsView;

/**
 *
 * Created by xiao on 2017/1/5 0005.
 */

public class ContentsPresenter extends AbsBasePresenter<ContentsView> {

    private ContentsView contentsView;
    private final ContentsModel contentsModel;

    public ContentsPresenter(ContentsView contentsView) {
        this.contentsView = contentsView;

        contentsModel = new ContentsModel();
    }

    public void requestData(int type) {
        contentsModel.requestData(type, new OnCompleteDataListener<Contents>() {
            @Override
            public void onComplete(Contents result) {
                contentsView.showData(result);
            }

            @Override
            public void onError(String errorMsg) {
                contentsView.showErrorMessage(errorMsg);
            }
        });
    }
}
