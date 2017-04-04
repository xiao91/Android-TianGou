package com.xiao91.sky.mvp.presenter;

import com.xiao91.sky.bean.AddFocus;
import com.xiao91.sky.bean.CancelFocus;
import com.xiao91.sky.mvp.model.FocusModel;
import com.xiao91.sky.mvp.model.OnCompleteDataListener;
import com.xiao91.sky.mvp.view.FocusView;

/**
 *
 * Created by xl on 2017/2/17 0017.
 */

public class FocusPre {

    private FocusView focusView;
    private final FocusModel focusModel;

    public FocusPre(FocusView focusView) {
        this.focusView = focusView;

        focusModel = new FocusModel();
    }

    public void requestAddFocus(String userId, String uid) {
        focusModel.requestAddFocus(userId, uid, new OnCompleteDataListener<AddFocus>() {
            @Override
            public void onComplete(AddFocus result) {
                focusView.showAddFocus(result);
            }

            @Override
            public void onError(int errorCode) {
                //...
            }
        });
    }

    public void requestCancelFocus(String focusId) {
        focusModel.requestCancelFocus(focusId, new OnCompleteDataListener<CancelFocus>() {
            @Override
            public void onComplete(CancelFocus result) {
                focusView.showCancelFocus(result);
            }

            @Override
            public void onError(int errorCode) {
                // ...
            }
        });
    }
}
